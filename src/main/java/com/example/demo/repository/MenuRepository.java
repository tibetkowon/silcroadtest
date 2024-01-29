package com.example.demo.repository;

import com.example.demo.entity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class MenuRepository {
    private static final Map<Long, Menu> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public List<Menu> findAll() {
        return store.values().stream().toList();
    }

    public void saveAll(List<Menu> menuList) {
        menuList.forEach(menu -> {
            if (menu.getMenuId() == null) {
                menu.setMenuId(++sequence);
                store.put(sequence, menu);
            } else {
                store.put(menu.getMenuId(), menu);
            }
        });
    }
}
