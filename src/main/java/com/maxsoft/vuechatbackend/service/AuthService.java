package com.maxsoft.vuechatbackend.service;

import com.maxsoft.vuechatbackend.controller.dto.AccountDto;
import com.maxsoft.vuechatbackend.controller.dto.AuthRequestDto;
import com.maxsoft.vuechatbackend.controller.dto.mapper.AccountMapper;
import com.maxsoft.vuechatbackend.controller.dto.mapper.EncryptAccountKeyMapper;
import com.maxsoft.vuechatbackend.entity.Account;
import com.maxsoft.vuechatbackend.entity.EncryptAccountKey;
import com.maxsoft.vuechatbackend.repository.AccountRepository;
import com.maxsoft.vuechatbackend.repository.EncryptAccountKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final EncryptAccountKeyRepository encryptAccountKeyRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountDto login(AuthRequestDto authRequestDto) {
        return null;
    }

    @Transactional
    public AccountDto register(AuthRequestDto authRequestDto) {
        checkUsernameExists(authRequestDto.getUsername());

        Account account = Account.builder()
                .username(authRequestDto.getUsername())
                .firstname(authRequestDto.getFirstname())
                .lastname(authRequestDto.getLastname())
                .publicKey(authRequestDto.getKeyRsa().getPublicKey())
                .password(passwordEncoder.encode(authRequestDto.getPassword()))
                .build();

        account = accountRepository.save(account);

        EncryptAccountKey keyRsa = EncryptAccountKeyMapper.prepareRsaForDatabase(authRequestDto, account.getId());
        EncryptAccountKey keyAes = EncryptAccountKeyMapper.prepareAesForDatabase(authRequestDto, account.getId());
        encryptAccountKeyRepository.saveAll(List.of(keyRsa, keyAes));

        return AccountMapper.convertToDto(account);
    }

    private void checkUsernameExists(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already exists with username: " + username);
        }
    }
}
