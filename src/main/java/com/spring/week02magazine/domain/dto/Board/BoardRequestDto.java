package com.spring.week02magazine.domain.dto.Board;

import com.spring.week02magazine.domain.dto.AccountDetailsDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.service.AccountDetailsService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String content;
    private String img_url;
    private String board_status;
    private Account account_id;

    public void insertAccountId(Account account_id) {
        this.account_id = account_id;
    }

    @Builder
    public BoardRequestDto(String content, String img_url, String board_status, Account account_id) {
        this.content = content;
        this.img_url = img_url;
        this.board_status = board_status;
        this.account_id = account_id;
    }

    public static Board toEntity(BoardRequestDto requestDto) {
        return Board.builder()
                .content(requestDto.getContent())
                .imgUrl(requestDto.getImg_url())
                .boardStatus(requestDto.getBoard_status())
                .accountId(requestDto.getAccount_id())
                .build();
    }
}
