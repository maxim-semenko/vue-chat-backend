package com.maxsoft.vuechatbackend.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Builder
@ToString
public class EncryptedKeyDto {

    @Size(min = 344, max = 2284)
    String value;

    String digest;

    String signature;
}

