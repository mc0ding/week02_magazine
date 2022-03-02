package com.spring.week02magazine.domain.repository;

import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import com.spring.week02magazine.domain.entity.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시물 저장 확인")
    void saveBoard() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = new BoardRequestDto().toEntity(new BoardRequestDto("테스트 내용", "/imgaddress.img", "right", account));
        // when
        Board savedBoard = boardRepository.save(board);
        // then
        assertThat(board).isSameAs(savedBoard);
        assertThat(savedBoard.getBoardId()).isNotNull();
        assertThat(board.getContent()).isEqualTo(savedBoard.getContent());
        assertThat(board.getImgUrl()).isEqualTo(savedBoard.getImgUrl());
        assertThat(board.getBoardStatus()).isEqualTo(savedBoard.getBoardStatus());
        assertThat(board.getAccount()).isSameAs(savedBoard.getAccount());
        assertThat(boardRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("저장된 게시물 확인")
    void listBoard() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board savedBoard = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 내용", "/imgaddress.img", "right", account)));
        Board savedBoard2 = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 두번째 내용", "/imgaddress2.img", "left", account)));
        // when
        Board findBoard = boardRepository.findByBoardId(savedBoard.getBoardId()).orElseThrow(() -> new IllegalArgumentException("잘못된 값 입니다 : " + savedBoard.getBoardId()));
        Board findBoard2 = boardRepository.findByBoardId(savedBoard2.getBoardId()).orElseThrow(() -> new IllegalArgumentException("잘못된 값 입니다 : " + savedBoard2.getBoardId()));
        // then
        assertThat(boardRepository.count()).isEqualTo(2);
        assertThat(findBoard.getContent()).isEqualTo("테스트 내용");
        assertThat(findBoard.getImgUrl()).isEqualTo("/imgaddress.img");
        assertThat(findBoard.getBoardStatus()).isEqualTo("right");
        assertThat(findBoard2.getContent()).isEqualTo("테스트 두번째 내용");
        assertThat(findBoard2.getImgUrl()).isEqualTo("/imgaddress2.img");
        assertThat(findBoard2.getBoardStatus()).isEqualTo("left");
    }

    @Test
    @DisplayName("삭제된 게시물 확인")
    void deleteBoard() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board savedBoard = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 삭제 내용", "/img.img", "right", account)));
        // when
        boardRepository.delete(savedBoard);
        // then
        assertThat(boardRepository.count()).isEqualTo(0);
    }
}