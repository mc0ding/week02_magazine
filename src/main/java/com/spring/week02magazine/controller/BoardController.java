package com.spring.week02magazine.controller;

import com.spring.week02magazine.domain.dto.AccountDetailsDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardResponseDto;
import com.spring.week02magazine.domain.model.BoardSuccess;
import com.spring.week02magazine.domain.model.PostSuccess;
import com.spring.week02magazine.domain.model.Success;
import com.spring.week02magazine.service.AccountDetailsService;
import com.spring.week02magazine.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final AccountDetailsService accountDetailsService;

    @GetMapping("/board")
    public ResponseEntity<BoardSuccess> listBoard() {
        List<BoardResponseDto> boardResponseDtoList = boardService.listBoard();
        return new ResponseEntity<>(new BoardSuccess("success", "전체 게시판 목록 조회", boardResponseDtoList), HttpStatus.OK);
    }
    @PostMapping("/board")
    public ResponseEntity<PostSuccess> creatBoard(@RequestBody BoardRequestDto requestDto) {
        Long accountId = accountDetailsService.getMyUserWithAuthorities().getAccount_id();
        Long id = boardService.creatBoard(requestDto, accountId);
        return new ResponseEntity<>(new PostSuccess("success", "게시물 등록이 완료되었습니다.", id), HttpStatus.OK);
    }
    @PutMapping("/board/{boardId}")
    public ResponseEntity<Success> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto) {
        boardService.updateBoard(boardId, requestDto);
        return new ResponseEntity<>(new Success("success", "게시물 수정이 완료되었습니다."), HttpStatus.OK);
    }
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Success> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(new Success("success", "게시물 삭제가 완료되었습니다."), HttpStatus.OK);
    }
}
