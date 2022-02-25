package com.spring.week02magazine.domain.dto.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardIdResponseDto {
    private Long boardId;
    @Builder
    public BoardIdResponseDto(Long boardId) {
        this.boardId = boardId;
    }
}
