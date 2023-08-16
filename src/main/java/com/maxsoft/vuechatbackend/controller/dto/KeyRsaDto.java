package com.maxsoft.vuechatbackend.controller.dto;


import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@ToString
public class KeyRsaDto {

    /**
     * Encoded by server's publicKey only when registration
     */
    @Size(min = 2284, max = 2284)
    String privateKey;

    /**
     * Store in database
     */
    private String publicKey;

    @NotEmpty
    String digest;

    @NotEmpty
    String signature;
}
