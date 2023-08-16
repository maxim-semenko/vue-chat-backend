package com.maxsoft.vuechatbackend.entity;

import com.maxsoft.vuechatbackend.entity.enums.EncryptKeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "encrypt_account_key")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EncryptAccountKey {

    @Id
    @GeneratedValue
    private UUID id;

    @Lob
    private byte[] value;

    @Column(nullable = false)
    private UUID accountId;

    @Lob
    private byte[] digest;

    @Lob
    private byte[] signature;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EncryptKeyType type;

}
