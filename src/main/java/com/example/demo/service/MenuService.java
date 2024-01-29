package com.example.demo.service;

import com.example.demo.controller.dto.MenuDto;
import com.example.demo.entity.Menu;
import com.example.demo.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository repository;

    public List<MenuDto> findAllMenu() {
        List<Menu> menuList = repository.findAll();
        log.debug("menuList >> {}", menuList);
        return this.menuEntityToDto(menuList);
    }

    private List<MenuDto> menuEntityToDto(List<Menu> menuList) {
        if (menuList != null && !menuList.isEmpty()) {
            return menuList.stream().map(menu -> {
                List<Menu> childList = menuList.stream().filter(m -> m.getParentId() != null && m.getParentId().equals(menu.getMenuId())).toList();
                return MenuDto.builder().id(menu.getMenuId())
                        .name(menu.getName())
                        .index(menu.getIndex())
                        .childList(this.menuEntityToDto(childList))
                        .build();
            }).sorted(Comparator.comparing(MenuDto::getIndex)).toList();
        } else {
            return new ArrayList<>();
        }
    }
}
