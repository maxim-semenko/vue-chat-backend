package com.maxsoft.vuechatbackend.controller;

import com.maxsoft.vuechatbackend.controller.dto.AccountDto;
import com.maxsoft.vuechatbackend.controller.dto.AuthRequestDto;
import com.maxsoft.vuechatbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @RequestMapping("/login")
    public ResponseEntity<AccountDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.login(authRequestDto));
    }

    @RequestMapping("/register")
    public ResponseEntity<AccountDto> register(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.register(authRequestDto));
    }

}
