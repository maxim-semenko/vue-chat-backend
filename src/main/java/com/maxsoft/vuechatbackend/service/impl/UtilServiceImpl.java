package com.maxsoft.vuechatbackend.service.impl;

import com.maxsoft.vuechatbackend.entity.Utility;
import com.maxsoft.vuechatbackend.repository.UtilRepository;
import com.maxsoft.vuechatbackend.service.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilServiceImpl implements UtilService {

    private final UtilRepository utilRepository;

    @Override
    public UUID getServerUserId() {
        String id = utilRepository.findById(Utility.Key.SERVER_ACCOUNT_ID.name()).orElseThrow(() -> {
            log.warn("No server user ID in utilities");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }).getUtilValue();
        return UUID.fromString(id);
    }
}
