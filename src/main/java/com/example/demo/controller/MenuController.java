package com.example.demo.controller;

import com.example.demo.controller.dto.MenuDto;
import com.example.demo.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MenuController {
    private final MenuService service;

    @GetMapping("/menu-list")
    public List<MenuDto> getList() {
        return service.findAllMenu();
    }
}
