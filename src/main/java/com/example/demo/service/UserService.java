package com.example.demo.service;

import com.example.demo.config.jwt.JwtProvider;
import com.example.demo.controller.dto.InsertUserDto;
import com.example.demo.controller.dto.LoginDto;
import com.example.demo.controller.dto.TokenDto;
import com.example.demo.controller.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtProvider;

    public UserDto saveUser(InsertUserDto dto) {
        User user = User.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword()))
                .desc(dto.getDesc())
                .build();

        log.debug("insert User :: {}", user);
        User save = repository.save(user);
        log.debug("saved User :: {}", save);

        return UserDto.builder()
                .id(save.getId())
                .userId(save.getUserId())
                .userName(save.getName())
                .desc(save.getDesc())
                .build();
    }

    public TokenDto login(LoginDto loginDto) {
        User user = repository.findByUserId(loginDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디"));
        if (!encoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }
        return jwtProvider.makeToken(user.getId());
    }
}
