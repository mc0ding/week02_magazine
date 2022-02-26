package com.spring.week02magazine.domain.dto.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardIdResponseDto {
    private Long board_id;
    @Builder
    public BoardIdResponseDto(Long board_id) {
        this.board_id = board_id;
    }
}
