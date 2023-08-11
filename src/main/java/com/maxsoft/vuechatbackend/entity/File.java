package com.maxsoft.vuechatbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class File {

    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    private String data;

    @NotNull
    private UUID targetId;

    private String number;

    private UUID messageId;
}
