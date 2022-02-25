package com.spring.week02magazine.domain.dto.LikePost;

import com.spring.week02magazine.domain.entity.LikePost;
import lombok.Getter;

@Getter
public class LikePostAccountResponseDto {
    private final Long boardId;

    public LikePostAccountResponseDto(LikePost boardId) {
        this.boardId = boardId.getBoard().getId();
    }
}
