package com.maxsoft.vuechatbackend.service;

import com.maxsoft.vuechatbackend.dto.AccountDto;
import com.maxsoft.vuechatbackend.dto.AuthRequestDto;
import com.maxsoft.vuechatbackend.entity.Account;
import com.maxsoft.vuechatbackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    public AccountDto login(AuthRequestDto authRequestDto) {
        return null;
    }

    public AccountDto register(AuthRequestDto authRequestDto) {
        Account account = Account.builder()
                .username(authRequestDto.getUsername())
                .password(passwordEncoder.encode(authRequestDto.getPassword()))
                .build();

        accountRepository.save(account);

        return null;
    }
}
