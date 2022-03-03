package com.spring.week02magazine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.week02magazine.domain.dto.Account.AccountLoginRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountResponseDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import com.spring.week02magazine.service.AccountService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.http.MatcherType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    @DisplayName("[POST] 회원가입 기능 테스트")
    public void registerTest() throws Exception {
        //given
        AccountRegisterRequestDto account = AccountRegisterRequestDto.builder()
                .account_email("aa@test.com")
                .password("123123")
                .password_check("123123")
                .account_name("dlfma")
                .build();
//        given(accountService.register(new AccountRegisterRequestDto())).willReturn(any());
        //when - then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/register")
                        .content(objectMapper.writeValueAsString(account))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", Matchers.is("success")))
                .andExpect(jsonPath("msg", Matchers.is("회원가입이 완료되었습니다.")));
    }

    @Test
    @DisplayName("[POST] 로그인 기능 테스트")
    public void loginTest() throws Exception {
        //given
        AccountLoginRequestDto account = AccountLoginRequestDto.builder().email("aa@test.com").password("123123").build();
        given(accountService.login(new AccountLoginRequestDto())).willReturn(ArgumentMatchers.any((AccountResponseDto.class)));

        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/login")
                        .content(objectMapper.writeValueAsString(account))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", Matchers.is("success")))
                .andExpect(jsonPath("msg", Matchers.is("로그인에 성공하셨습니다.")));
    }

}