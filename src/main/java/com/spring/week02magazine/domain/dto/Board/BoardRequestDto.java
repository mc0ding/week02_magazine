package com.spring.week02magazine.domain.dto.Board;

import com.spring.week02magazine.domain.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String content;
    private String imgUrl;
    private String layout;

    @Builder
    public BoardRequestDto(String content, String imgUrl, String layout) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.layout = layout;
    }

    public static Board toEntity(BoardRequestDto requestDto) {
        return Board.builder()
                .content(requestDto.getContent())
                .imgUrl(requestDto.getImgUrl())
                .layout(requestDto.getLayout())
                .build();
    }
}
