package com.maxsoft.vuechatbackend.controller.dto.mapper;

import com.maxsoft.vuechatbackend.controller.dto.AccountDto;
import com.maxsoft.vuechatbackend.entity.Account;

public class AccountMapper {

    public static AccountDto convertToDto(Account account) {
        if (account == null) {
            return null;
        }

        return AccountDto.builder()
                .id(account.getId())
                .username(account.getUsername())
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .publicKey(account.getPublicKey())
                .build();
    }

//    public static AccountDto convertToDto(Account account) {
//
//        if (account == null) {
//            return null;
//        }
//
//        return AccountDto.builder()
//                .id(account.getId())
//                .username(account.getUsername())
//                .firstname(account.getFirstname())
//                .lastname(account.getLastname())
//                .publicKey(account.getPublicKey())
//                .encodedPrivateKey(encodedPrivateKey)
//                .build();
//    }

}
