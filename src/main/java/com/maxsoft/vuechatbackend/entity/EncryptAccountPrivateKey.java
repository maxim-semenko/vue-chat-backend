package com.maxsoft.vuechatbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "encrypt_account_private_key")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EncryptAccountPrivateKey {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, updatable = false, length = 2048)
    private String value;

    @Column(nullable = false)
    private UUID accountId;

}
