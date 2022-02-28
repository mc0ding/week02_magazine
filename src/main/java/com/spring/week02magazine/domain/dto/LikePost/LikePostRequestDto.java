package com.spring.week02magazine.domain.dto.LikePost;

import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LikePostRequestDto {
    private Account account;
    private Board board;

    public void changeAccount(Account account) {
        this.account = account;
    }
    public void changeBoard(Board board) {
        this.board = board;
    }

    @Builder
    public LikePostRequestDto(Account account, Board board) {
        this.account = account;
        this.board = board;
    }

    public static LikePost toEntity(LikePostRequestDto requestDto) {
        return LikePost.builder()
                .account(requestDto.getAccount())
                .board(requestDto.getBoard())
                .build();
    }
}
