package com.maxsoft.vuechatbackend.controller.dto.mapper;

import com.maxsoft.vuechatbackend.controller.dto.AuthRequestDto;
import com.maxsoft.vuechatbackend.entity.EncryptAccountKey;
import com.maxsoft.vuechatbackend.entity.enums.EncryptKeyType;

import java.util.Base64;
import java.util.UUID;

public class EncryptAccountKeyMapper {

    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    public static EncryptAccountKey prepareRsaForDatabase(AuthRequestDto authRequestDto, UUID accountId) {
        return EncryptAccountKey.builder()
                .value(base64Decoder.decode(authRequestDto.getKeyRsa().getPrivateKey()))
                .digest(base64Decoder.decode(authRequestDto.getKeyRsa().getDigest()))
                .signature(base64Decoder.decode(authRequestDto.getKeyRsa().getDigest()))
                .type(EncryptKeyType.RSA)
                .accountId(accountId)
                .build();
    }

    public static EncryptAccountKey prepareAesForDatabase(AuthRequestDto authRequestDto, UUID accountId) {
        return EncryptAccountKey.builder()
                .value(base64Decoder.decode(authRequestDto.getKeyRsa().getPrivateKey()))
                .digest(base64Decoder.decode(authRequestDto.getKeyRsa().getDigest()))
                .type(EncryptKeyType.AES)
                .accountId(accountId)
                .build();
    }

}
