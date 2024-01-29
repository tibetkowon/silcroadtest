package com.example.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class InsertUserDto {
    private String userId;
    private String name;
    private String password;
    private String desc;
}
