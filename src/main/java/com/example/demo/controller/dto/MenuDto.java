package com.example.demo.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MenuDto {
    private Long id;
    private String name;
    private int index;
    private List<MenuDto> childList;
}
