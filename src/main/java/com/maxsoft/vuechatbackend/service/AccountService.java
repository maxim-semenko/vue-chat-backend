package com.maxsoft.vuechatbackend.service;

import com.maxsoft.vuechatbackend.controller.dto.AccountDto;
import com.maxsoft.vuechatbackend.controller.dto.mapper.AccountMapper;
import com.maxsoft.vuechatbackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountDto findById(UUID id) {
        return accountRepository.findById(id)
                .map(AccountMapper::convertToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found by id: " + id));
    }

}
