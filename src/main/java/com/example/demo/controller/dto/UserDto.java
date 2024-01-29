package com.example.demo.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserDto {
    private Long id;
    private String userId;
    private String userName;
    private String desc;
}
