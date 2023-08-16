package com.maxsoft.vuechatbackend.controller;


import com.maxsoft.vuechatbackend.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/utils")
@RequiredArgsConstructor
public class UtilController {

    private final UtilService utilService;

    @RequestMapping("/public-key")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(utilService.getPublicKey());
    }
}
