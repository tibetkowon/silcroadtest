package com.example.demo;

import com.example.demo.controller.dto.MenuDto;
import com.example.demo.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MenuTest {

    @Autowired
    private MenuService service;


    @DisplayName("메뉴 목록 조회")
    @Test
    void getAllMenu() {
    //when
    List<MenuDto> menuList = service.findAllMenu();

    //then
    assertThat(menuList).isNotNull().isNotEmpty();
    }


}
