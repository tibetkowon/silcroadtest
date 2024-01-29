package com.example.demo;

import com.example.demo.controller.dto.InsertUserDto;
import com.example.demo.controller.dto.LoginDto;
import com.example.demo.controller.dto.TokenDto;
import com.example.demo.controller.dto.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestMethodOrder(MethodOrderer.Random.class)
public class UserTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void beforeInsertUser() {
        InsertUserDto insert = InsertUserDto.builder()
                .userId("test1")
                .name("테스트")
                .password("1234qwer")
                .desc("test")
                .build();
        service.saveUser(insert);
    }

    @AfterEach
    void afterDeleteUser() {
        repository.clear();
    }

    @DisplayName("회원 생성")
    @Test
    void insertUser() {
        //given
        InsertUserDto insert = InsertUserDto.builder()
                .userId("test222")
                .name("테스트")
                .password("1234qwer")
                .desc("test")
                .build();
        //when
        UserDto userDto = service.saveUser(insert);

        //then
        assertThat(userDto.getId()).isNotZero().isNotNull();
        assertThat(userDto.getUserId()).isEqualTo("test222");
    }

    @DisplayName("중복 ID 회원가입 불가")
    @Test
    void duplicateUserId() {
        InsertUserDto insert = InsertUserDto.builder()
                .userId("test1")
                .name("중복테스트")
                .password("qwer1234")
                .desc("중복아이디")
                .build();

        assertThatThrownBy(() -> service.saveUser(insert))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복 아이디");
    }

    @DisplayName("로그인")
    @Test
    void loginTest() {
        //given
        LoginDto loginDto = LoginDto.builder()
                .userId("test1")
                .password("1234qwer")
                .build();

        //when
        TokenDto tokenDto = service.login(loginDto);

        //then
        assertThat(tokenDto.getAccessToken()).isNotEmpty();
        assertThat(tokenDto.getRefreshToken()).isNotEmpty();
    }
}
