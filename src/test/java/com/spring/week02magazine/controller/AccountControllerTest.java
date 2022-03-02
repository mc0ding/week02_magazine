package com.spring.week02magazine.controller;

import com.spring.week02magazine.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mocMvc;

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("회원가입 기능 테스트")
    void register() {
    }

    @Test
    void login() {
    }
}