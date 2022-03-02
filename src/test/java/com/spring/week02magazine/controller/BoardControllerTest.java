package com.spring.week02magazine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.week02magazine.domain.dto.Board.BoardResponseDto;
import com.spring.week02magazine.service.BoardService;
import org.h2.api.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    @DisplayName("GET 게시글 전체 리스트 조회")
    void listBoard() throws Exception {
        // given
        given(boardService.listBoard())
                .willReturn(Arrays.asList(BoardResponseDto.builder()
                        .account_id(1L)
                        .account_name("user")
                        .board_id(1L)
                        .content("게시글 내용")
                        .like(3)
                        .time(LocalDateTime.now())
                        .img_url("/imgaddress.png")
                        .board_status("right")
                        .build()));
        // when & then
        mockMvc.perform(get("/api/board"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].account_name").value("user"))
                .andExpect(jsonPath("$.data[0].content").value("게시글 내용"))
                .andExpect(jsonPath("$.code").value(status().isCreated()));
//                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @Test
    void creatBoard() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void deleteBoard() {
    }
}