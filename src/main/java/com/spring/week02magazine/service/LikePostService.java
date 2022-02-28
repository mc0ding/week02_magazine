package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.LikePost.LikePostRequestDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import com.spring.week02magazine.domain.repository.AccountRepository;
import com.spring.week02magazine.domain.repository.BoardRepository;
import com.spring.week02magazine.domain.repository.LikePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikePostService {
    private final LikePostRepository likePostRepository;
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public int addLikePost(Long boardId, Long accountId, LikePostRequestDto requestDto) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        Optional<LikePost> likePost = likePostRepository.findByAccountAndBoard(account.get(), board.get());
        if (likePost.isPresent()) { throw new IllegalArgumentException("좋아요에 대한 잘못된 접근입니다."); }
        requestDto.changeAccount(account.get());
        requestDto.changeBoard(board.get());
        likePostRepository.save(LikePostRequestDto.toEntity(requestDto));
        int likeCount = likePostRepository.findLikePostByBoard(board.get()).size();
        return likeCount;
    }
    @Transactional
    public int removeLikePost(Long boardId, Long accountId) {
        likePostRepository.delete(likePostRemoveValidation(accountId, boardId));
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        int likeCount = likePostRepository.findLikePostByBoard(board.get()).size();
        return likeCount;
    }
    private LikePost likePostRemoveValidation(Long accountId, Long boardId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        Optional<LikePost> likePost = likePostRepository.findByAccountAndBoard(account.get(), board.get());
        if (!likePost.isPresent()) { throw new IllegalArgumentException("좋아요에 대한 잘못된 접근입니다."); }
        if (!likePost.get().getAccount().getAccountId().equals(accountId)) {
            throw new IllegalArgumentException("좋아요에 해당하는 사용자가 아닙니다.");
        }
        return likePost.get();
    }
}
