package com.example.demo.config;

import com.example.demo.entity.Menu;
import com.example.demo.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyApplicationRunner implements ApplicationRunner {
    private final MenuRepository menuRepository;

    @Override
    public void run(ApplicationArguments args) {
        Menu users = Menu.builder()
                .name("Users")
                .index(0)
                .build();

        Menu products = Menu.builder()
                .name("Products")
                .index(0)
                .build();

        Menu settings = Menu.builder()
                .name("Settings")
                .index(0)
                .build();

        List<Menu> menuList = Arrays.asList(users, products, settings);
        menuRepository.saveAll(menuList);
    }
}