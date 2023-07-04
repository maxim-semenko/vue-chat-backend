package com.maxsoft.vuechatbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxsoft.vuechatbackend.entity.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID senderId;

    @NotNull
    private UUID receiverId;

    @NotNull
    private UUID chatId;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private MessageType type;

    @Column(length = 2048)
    private String content;

    private Boolean isHasAttachments;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<File> files = new HashSet<>();

    @Column(length = 361)
    private String nonce;

    private Date createdAt;

    private Date updatedAt;

}
