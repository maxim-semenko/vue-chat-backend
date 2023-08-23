package com.maxsoft.vuechatbackend.service;

import com.maxsoft.vuechatbackend.entity.Account;
import com.maxsoft.vuechatbackend.entity.Utility;
import com.maxsoft.vuechatbackend.repository.AccountRepository;
import com.maxsoft.vuechatbackend.repository.UtilRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilService {

    private final UtilRepository utilRepository;
    private final AccountRepository accountRepository;

    public String getPublicKey() {
        UUID serverAccountId = getServerUserId();
        Account serverAccount = accountRepository
                .findById(serverAccountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        return serverAccount.getPublicKey();
    }

    public String getServerPrivateKey() {
        return utilRepository
                .findById(Utility.Key.SERVER_ACCOUNT_PRIVATE_KEY.name())
                .orElseThrow(() -> {
                    log.warn("No server user ID in utilities");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }).getUtilValue();
    }

    private UUID getServerUserId() {
        String id = utilRepository.findById(Utility.Key.SERVER_ACCOUNT_ID.name()).orElseThrow(() -> {
            log.warn("No server user ID in utilities");
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }).getUtilValue();
        return UUID.fromString(id);
    }

}
