package com.example.demo.entity;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Menu {
    @Setter
    private Long menuId;
    private Long parentId;
    private String name;
    private int index;
}
