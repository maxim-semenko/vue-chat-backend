package com.maxsoft.vuechatbackend.controller.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@ToString
public class KeyAesDto {

    @Size(min = 344, max = 344)
    String value;
    String digest;
}
