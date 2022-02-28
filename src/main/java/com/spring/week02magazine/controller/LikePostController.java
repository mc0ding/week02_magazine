package com.spring.week02magazine.controller;

import com.spring.week02magazine.domain.dto.AccountDetailsDto;
import com.spring.week02magazine.domain.dto.LikePost.LikePostRequestDto;
import com.spring.week02magazine.domain.model.LikePostSuccess;
import com.spring.week02magazine.service.AccountDetailsService;
import com.spring.week02magazine.service.LikePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/like")
@RequiredArgsConstructor
public class LikePostController {
    private final LikePostService likePostService;
    private final AccountDetailsService accountDetailsService;

    @PostMapping("/{boardId}")
    public ResponseEntity<LikePostSuccess> addLikePost(@PathVariable Long boardId, LikePostRequestDto requestDto) {
        int likeCount = likePostService.addLikePost(boardId, accountValidation(), requestDto);
        return new ResponseEntity<>(new LikePostSuccess("success", "좋아요 완료!", likeCount), HttpStatus.OK);
    }
    @DeleteMapping("/{boardId}")
    public ResponseEntity<LikePostSuccess> removeLikePost(@PathVariable Long boardId) {
        int likeCount = likePostService.removeLikePost(boardId, accountValidation());
        return new ResponseEntity<>(new LikePostSuccess("success", "좋아요 삭제 완료!", likeCount), HttpStatus.OK);
    }
    private Long accountValidation() {
        AccountDetailsDto accountDetailsDto = accountDetailsService.getMyUserWithAuthorities();
        if (accountDetailsDto == null) { throw new IllegalArgumentException("로그인이 필요합니다."); }
        return accountDetailsDto.getAccount_id();
    }
}