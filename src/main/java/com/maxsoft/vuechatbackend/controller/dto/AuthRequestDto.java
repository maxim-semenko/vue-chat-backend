package com.maxsoft.vuechatbackend.controller.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@ToString
public class AuthRequestDto {

    @NotEmpty
    @Size(min = 2, max = 30)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 255)
    private String password;

    @Size(min = 2, max = 20)
    private String firstname;

    @Size(min = 2, max = 20)
    private String lastname;

    private String publicKey;

    private EncryptedKeyDto keyRsa;

    private EncryptedKeyDto keyAes;
}
