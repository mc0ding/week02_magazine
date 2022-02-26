package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.LikePost.LikePostRequestDto;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import com.spring.week02magazine.domain.repository.BoardRepository;
import com.spring.week02magazine.domain.repository.LikePostRepository;
import com.spring.week02magazine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikePostService {
    private final LikePostRepository likePostRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public int addLikePost(Long boardId) {
        if (accountDetails.getUser() == null) { throw new IllegalArgumentException("로그인이 필요합니다."); }
        LikePost likePost = likePostValidation(accountDetails.getUser().getId(), boardId);
        likePost.changeAccount(accountDetails.getUser());
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        likePost.changeBoard(board);
        likePostRepository.save(LikePostRequestDto.toEntity());
        int likeCount = boardRepository.findAllBy().size();
        return likeCount;
    }

    @Transactional
    public int removeLikePost(Long boardId) {
        if (accountDetails.getUser() == null) { throw new IllegalArgumentException("로그인이 필요합니다."); }
        likePostRepository.delete(likePostValidation(accountDetails.getUser().getId(), boardId));
        int likeCount = boardRepository.findAllBy().size();
        return likeCount;
    }

    private LikePost likePostValidation(Long accountId, Long boardId) {
        LikePost likePost = likePostRepository.findByAccount_IdAndBoard_Id(accountId, boardId);
        if (likePost != null) { throw new IllegalArgumentException("좋아요에 대한 잘못된 접근입니다."); }
        if (likePost.getAccount().getId().equals(accountId)) {
            throw new IllegalArgumentException("좋아요에 해당하는 사용자가 아닙니다.");
        }
        return likePost;
    }
}
