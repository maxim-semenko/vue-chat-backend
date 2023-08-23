package com.maxsoft.vuechatbackend.controller.dto.mapper;

import com.maxsoft.vuechatbackend.controller.dto.EncryptedKeyDto;
import com.maxsoft.vuechatbackend.entity.EncryptAccountKey;
import com.maxsoft.vuechatbackend.entity.enums.EncryptKeyType;

import java.util.Base64;
import java.util.UUID;

public class EncryptAccountKeyMapper {

    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    public static EncryptAccountKey prepareRsaForDatabase(EncryptedKeyDto keyRsa, UUID accountId) {
        return EncryptAccountKey.builder()
                .value(base64Decoder.decode(keyRsa.getValue()))
                .digest(base64Decoder.decode(keyRsa.getDigest()))
                .accountId(accountId)
                .type(EncryptKeyType.RSA)
                .build();
    }

    public static EncryptedKeyDto prepareRsaForJson(byte[] value, byte[] digest) {
        return EncryptedKeyDto.builder()
                .value(base64Encoder.encodeToString(value))
                .digest(base64Encoder.encodeToString(digest))
                .build();
    }

    public static EncryptAccountKey prepareAesForDatabase(EncryptedKeyDto keyAes, UUID accountId) {
        return EncryptAccountKey.builder()
                .value(base64Decoder.decode(keyAes.getValue()))
                .digest(base64Decoder.decode(keyAes.getDigest()))
                .signature(base64Decoder.decode(keyAes.getSignature()))
                .accountId(accountId)
                .type(EncryptKeyType.AES)
                .build();
    }

}
