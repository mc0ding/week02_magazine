package com.spring.week02magazine.repository;

import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.dto.LikePost.LikePostRequestDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
class LikePostRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    LikePostRepository likePostRepository;

    @Test
    @DisplayName("게시물 좋아요 추가 확인")
    void addLike() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 내용", "/imgaddress.img", "right", account)));
        LikePost likePost = new LikePostRequestDto(account, board).toEntity(new LikePostRequestDto(account, board));
        // when
        LikePost savedLikePost = likePostRepository.save(likePost);
        // then
        assertThat(likePost).isSameAs(savedLikePost);
        assertThat(savedLikePost.getLikeId()).isNotNull();
        assertThat(likePost.getAccount()).isSameAs(savedLikePost.getAccount());
        assertThat(likePost.getBoard()).isSameAs(savedLikePost.getBoard());
        assertThat(likePostRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물 좋아요 삭제 확인")
    void removeLike() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 내용", "/imgaddress.img", "right", account)));
        LikePost savedLikePost = likePostRepository.save(new LikePostRequestDto(account, board).toEntity(new LikePostRequestDto(account, board)));
        // when
        likePostRepository.delete(savedLikePost);
        // then
        assertThat(likePostRepository.count()).isEqualTo(0);
    }
}