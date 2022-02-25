package com.spring.week02magazine.controller;

import com.spring.week02magazine.domain.model.LikePostSuccess;
import com.spring.week02magazine.security.UserDetailsImpl;
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

    @PostMapping("/{boardId}")
    public ResponseEntity<LikePostSuccess> addLikePost(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl accountDetails) {
        int likeCount = likePostService.addLikePost(boardId, accountDetails);
        return new ResponseEntity<>(new LikePostSuccess("success", "좋아요 완료!", likeCount), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<LikePostSuccess> removeLikePost(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl accountDetails) {
        int likeCount = likePostService.removeLikePost(boardId, accountDetails);
        return new ResponseEntity<>(new LikePostSuccess("success", "좋아요 삭제 완료!", likeCount), HttpStatus.OK);
    }
}