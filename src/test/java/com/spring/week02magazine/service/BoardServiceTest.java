package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.repository.AccountRepository;
import com.spring.week02magazine.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

// 먼저 작동하고 있는 BoardService 에 Account 연관관계 설정 되어있는 부분으로 인해
// Mock 을 이용한 기능 테스트가 어려움 : SpringBootTest 로 진행
@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("게시물 등록 동작 확인")
    void creatBoard() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        BoardRequestDto requestDto = new BoardRequestDto("테스트 게시물 내용", "/img_url.img", "right", account);
        // when
        boardService.creatBoard(requestDto, account.getAccountId());
        List<BoardResponseDto> boardList = boardService.listBoard();
        // then (board 저장 시 id 값만 반환하지만, repository에 그대로 저장 되었는지 확인하기 위해 내용 조회)
        Board responseDto = boardRepository.getById(boardList.get(0).getBoard_id());
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getContent()).isEqualTo("테스트 게시물 내용");
        assertThat(responseDto.getImgUrl()).isEqualTo("/img_url.img");
        assertThat(responseDto.getBoardStatus()).isEqualTo("right");
        assertThat(boardRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물 조회 동작 확인")
    void listBoard() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 게시물 내용", "/img_url.img", "right", account)));
        Board board2 = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 게시물 내용2", "/img_url2.img", "left", account)));
        // when
        List<BoardResponseDto> boardList = boardService.listBoard();
        // then (list 형식으로 전체를 조회하는 것을 대신하여 인덱스로 게시물 두 개의 내용을 하나씩 확인)
        assertThat(boardRepository.count()).isEqualTo(2); // 인덱스 조회 : desc 형식으로 불러오기 때문에 뒤에 저장한 게시물이 0번 인덱스
        assertThat(boardList.get(0).getContent()).isEqualTo("테스트 게시물 내용2");
        assertThat(boardList.get(0).getImg_url()).isEqualTo("/img_url2.img");
        assertThat(boardList.get(0).getBoard_status()).isEqualTo("left");
        assertThat(boardList.get(1).getContent()).isEqualTo("테스트 게시물 내용");
        assertThat(boardList.get(1).getImg_url()).isEqualTo("/img_url.img");
        assertThat(boardList.get(1).getBoard_status()).isEqualTo("right");
    }

    @Test
    @DisplayName("게시물 수정 동작 확인")
    void updateBoard() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 게시물 내용", "/img_url.img", "right", account)));
        BoardRequestDto requestDto2 = new BoardRequestDto("수정된 게시물 내용", "/img_url2.img", "left", account);
        List<BoardResponseDto> boardList = boardService.listBoard();
        // when
        boardService.updateBoard(boardList.get(0).getBoard_id(), requestDto2, account.getAccountId());
        // then
        Board responseDto = boardRepository.getById(boardList.get(0).getBoard_id());
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getContent()).isEqualTo("수정된 게시물 내용");
        assertThat(responseDto.getImgUrl()).isEqualTo("/img_url2.img");
        assertThat(responseDto.getBoardStatus()).isEqualTo("left");
        assertThat(boardRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물 삭제 동작 확인")
    void deleteBoard() {
        // given
        Account account = accountRepository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Board board = boardRepository.save(new BoardRequestDto().toEntity(new BoardRequestDto("테스트 게시물 내용", "/img_url.img", "right", account)));
        List<BoardResponseDto> boardList = boardService.listBoard();
        // when
        boardService.deleteBoard(boardList.get(0).getBoard_id(), account.getAccountId());
        // then
        assertThat(boardRepository.count()).isEqualTo(0);
    }
}