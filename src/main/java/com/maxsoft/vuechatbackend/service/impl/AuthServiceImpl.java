package com.maxsoft.vuechatbackend.service.impl;

import com.maxsoft.vuechatbackend.dto.AccountDto;
import com.maxsoft.vuechatbackend.dto.AuthRequestDto;
import com.maxsoft.vuechatbackend.entity.Account;
import com.maxsoft.vuechatbackend.repository.AccountRepository;
import com.maxsoft.vuechatbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AccountDto login(AuthRequestDto authRequestDto) {
        return null;
    }

    @Override
    public AccountDto register(AuthRequestDto authRequestDto) {
        Account account = Account.builder()
                .username(authRequestDto.getUsername())
                .build();


        return null;
    }
}
