package com.maxsoft.vuechatbackend.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AccountDto {

    private UUID id;

    private String username;

    private String firstname;

    private String lastname;

    private byte[] encodedPrivateKey;

}
