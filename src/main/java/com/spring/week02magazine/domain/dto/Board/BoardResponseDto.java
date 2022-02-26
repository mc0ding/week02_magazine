package com.spring.week02magazine.domain.dto.Board;

import com.spring.week02magazine.domain.dto.LikePost.LikePostBoardResponseDto;
import com.spring.week02magazine.domain.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {
    private final Long account_id;
    private final String account_name;
    private final Long board_id;
    private final String content;
    private final int like;
    private final List<LikePostBoardResponseDto> like_id;
    private final LocalDateTime time;
    private final String img_url;
    private final String board_status;

    @Builder
    public BoardResponseDto(Long account_id, String account_name, Long board_id, String content, String img_url, String board_status, LocalDateTime time, List<LikePostBoardResponseDto> like_id, int like) {
        this.account_id = account_id;
        this.account_name = account_name;
        this.board_id = board_id;
        this.content = content;
        this.like = like;
        this.like_id = like_id;
        this.time = time;
        this.img_url = img_url;
        this.board_status = board_status;
    }

    public static BoardResponseDto of(Board board) {
        List<LikePostBoardResponseDto> collect = board.getLikePostList().stream()
                .map(LikePostBoardResponseDto::new)
                .collect(Collectors.toList());
        int likeCount = collect.size();

        return BoardResponseDto.builder()
                .account_id(board.getAccountId().getId())
                .account_name(board.getAccountId().getAccountName())
                .board_id(board.getId())
                .content(board.getContent())
                .like(likeCount)
                .like_id(collect)
                .time(board.getModifiedAt())
                .img_url(board.getImgUrl())
                .board_status(board.getBoardStatus())
                .build();
    }
}