package com.example.demo.repository;

import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class UserRepository {
    private static final Map<Long, User> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public User save(User user) {
        store.values().stream().filter(u -> u.getUserId().equals(user.getUserId())).findAny().ifPresent(u -> {
            log.error("duplicate userId >> {}", user.getUserId());
            throw new IllegalArgumentException("중복 아이디");
        });

        user.setId(++sequence);
        store.put(sequence, user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByUserId(String userId) {
        return store.values().stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }

    public void clear() {
        store.clear();
    }
}
