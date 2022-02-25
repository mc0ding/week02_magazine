package com.spring.week02magazine.domain.model;

import com.spring.week02magazine.domain.dto.Board.BoardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardSuccess {
    private String result;
    private String msg;
    private List<BoardResponseDto> data;
}
