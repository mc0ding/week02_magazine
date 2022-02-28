package com.spring.week02magazine.domain.dto.LikePost;

import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikePostBoardResponseDto {
    private final Long accountId;

    public LikePostBoardResponseDto(LikePost accountId) {
        this.accountId = accountId.getAccount().getAccountId();
    }
}
