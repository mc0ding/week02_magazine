package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardResponseDto;
import com.spring.week02magazine.domain.dto.LikePost.LikePostRequestDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import com.spring.week02magazine.repository.AccountRepository;
import com.spring.week02magazine.repository.BoardRepository;
import com.spring.week02magazine.repository.LikePostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LikePostServiceTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private LikePostService likePostService;

    @Test
    @DisplayName("게시물 등록 동작 확인")
    void addLikePost() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 게시물 내용", "/img_url.img", "right", account)));
        LikePostRequestDto requestDto = new LikePostRequestDto(account, board);
        List<BoardResponseDto> boardList = boardService.listBoard();
        // when
        likePostService.addLikePost(boardList.get(0).getBoard_id(), account.getAccountId(), requestDto);
        // then
        Optional<LikePost> likePost = likePostRepository.findByAccountAndBoard(account, board);
        List<LikePost> likeCount = likePostRepository.findLikePostByBoard(board);
        assertThat(likePost).isNotNull();
        assertThat(likeCount.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물 등록 동작 확인")
    void removeLikePost() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 게시물 내용", "/img_url.img", "right", account)));
        LikePost likePost = likePostRepository.save(new LikePostRequestDto(account, board).toEntity(new LikePostRequestDto(account, board)));
        List<BoardResponseDto> boardList = boardService.listBoard();
        // when
        likePostService.removeLikePost(boardList.get(0).getBoard_id(), account.getAccountId());
        assertThat(likePostRepository.count()).isEqualTo(0);
        // then

    }
}