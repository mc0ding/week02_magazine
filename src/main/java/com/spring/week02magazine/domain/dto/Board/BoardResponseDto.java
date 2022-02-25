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
    private final Long accountId;
    private final String accountName;
    private final Long boardId;
    private final String content;
    private final String imgUrl;
    private final String layout;
    private final LocalDateTime time;
    private final List<LikePostBoardResponseDto> likeId;
    private final int like;

    @Builder
    public BoardResponseDto(Long accountId, String accountName, Long boardId, String content, String imgUrl, String layout, LocalDateTime time, List<LikePostBoardResponseDto> likeId, int like) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.boardId = boardId;
        this.content = content;
        this.imgUrl = imgUrl;
        this.layout = layout;
        this.time = time;
        this.likeId = likeId;
        this.like = like;
    }

    public static BoardResponseDto of(Board board) {
        List<LikePostBoardResponseDto> collect = board.getLikePostList().stream()
                .map(LikePostBoardResponseDto::new)
                .collect(Collectors.toList());
        int likeCount = collect.size();

        return BoardResponseDto.builder()
                .accountId(board.getAccountId().getId())
                .accountName(board.getAccountId().getUsername())
                .boardId(board.getId())
                .content(board.getContent())
                .imgUrl(board.getImgUrl())
                .layout(board.getLayout())
                .time(board.getModifiedAt())
                .likeId(collect)
                .like(likeCount)
                .build();
    }
}