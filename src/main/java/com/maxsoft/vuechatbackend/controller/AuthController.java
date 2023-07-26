package com.maxsoft.vuechatbackend.controller;

import com.maxsoft.vuechatbackend.dto.AccountDto;
import com.maxsoft.vuechatbackend.dto.AuthRequestDto;
import com.maxsoft.vuechatbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {

    private final AuthService authService;

    @RequestMapping("/login")
    public ResponseEntity<AccountDto> login(AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.login(authRequestDto));
    }

    @RequestMapping("/register")
    public ResponseEntity<AccountDto> register(AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.register(authRequestDto));
    }

}
