package com.example.demo.entity;


import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {
    @Setter
    private Long id;
    private String userId;
    private String name;
    private String password;
    private String desc;
}
