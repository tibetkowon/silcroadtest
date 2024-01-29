package com.example.demo.controller;

import com.example.demo.controller.dto.InsertUserDto;
import com.example.demo.controller.dto.LoginDto;
import com.example.demo.controller.dto.TokenDto;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping("/sign-up")
    public void insertUser(@RequestBody InsertUserDto dto) {
        service.saveUser(dto);
    }

    @PostMapping("/sign-in")
    public void loginUser(
            @RequestBody LoginDto dto,
            HttpServletResponse response) {
        TokenDto token = service.login(dto);

        Cookie authToken = new Cookie("authToken", token.getAccessToken());
        authToken.setHttpOnly(true);
        authToken.setSecure(true);

        Cookie refreshToken = new Cookie("refreshToken", token.getRefreshToken());
        refreshToken.setHttpOnly(true);
        refreshToken.setSecure(true);

        response.addCookie(authToken);
        response.addCookie(refreshToken);
    }
}
