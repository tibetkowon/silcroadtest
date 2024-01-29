package com.example.demo.controller.dto;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LoginDto {
    private String userId;
    private String password;
}
