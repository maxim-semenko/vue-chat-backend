package com.maxsoft.vuechatbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "public_key")
@Data
public class PublicKey {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, updatable = false, length = 512)
    private String value;

    @JsonIgnore
    private String encryptByPasswordValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    private Integer number;
}
