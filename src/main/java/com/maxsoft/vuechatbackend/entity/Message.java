package com.maxsoft.vuechatbackend.entity;

import com.maxsoft.vuechatbackend.entity.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.Date;
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

    @Enumerated(EnumType.STRING)
    @NotNull
    private MessageType type;

    @Lob
    private byte[] content;

    @Lob
    private byte[] signature;

    @Lob
    @NotNull
    private byte[] digest;

    private Boolean isHasAttachments;

    private Date createdAt;

    private Date updatedAt;

}
