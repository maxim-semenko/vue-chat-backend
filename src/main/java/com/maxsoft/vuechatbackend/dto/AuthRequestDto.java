package com.maxsoft.vuechatbackend.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
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
}
