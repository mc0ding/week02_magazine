package com.spring.week02magazine.domain.dto.Board;

import com.spring.week02magazine.domain.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String content;
    private String img_url;
    private String board_status;

    @Builder
    public BoardRequestDto(String content, String img_url, String board_status) {
        this.content = content;
        this.img_url = img_url;
        this.board_status = board_status;
    }

    public static Board toEntity(BoardRequestDto requestDto) {
        return Board.builder()
                .content(requestDto.getContent())
                .imgUrl(requestDto.getImg_url())
                .boardStatus(requestDto.getBoard_status())
                .build();
    }
}
