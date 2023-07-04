package com.maxsoft.vuechatbackend.service;

import com.maxsoft.vuechatbackend.dto.AccountDto;
import com.maxsoft.vuechatbackend.dto.AuthRequestDto;

public interface AuthService {

    AccountDto login(AuthRequestDto authRequestDto);

    AccountDto register(AuthRequestDto authRequestDto);

}
