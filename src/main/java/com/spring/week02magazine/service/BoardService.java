package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Board.BoardIdResponseDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.repository.AccountRepository;
import com.spring.week02magazine.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final AccountRepository accountRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> listBoard() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        return boardList.stream()
                .map(BoardResponseDto::of)
                .collect(Collectors.toList());
    }
    @Transactional
    public Long creatBoard(BoardRequestDto requestDto, Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        requestDto.insertAccount(account.get());
        return new BoardIdResponseDto(boardRepository.save(BoardRequestDto.toEntity(requestDto)).getBoardId()).getBoard_id();
    }
    @Transactional
    public void updateBoard(Long boardId, BoardRequestDto requestDto, Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        Board board = boardValidation(boardId, accountId);
        board.updateBoard(requestDto.getContent(), requestDto.getImg_url(), requestDto.getBoard_status(), account.get());
    }
    @Transactional
    public void deleteBoard(Long boardId, Long accountId) {
        boardRepository.delete(boardValidation(boardId, accountId));
    }
    private Board boardValidation(Long boardId, Long accountId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("???????????? ???????????? ????????????."));
        if (!board.getAccount().getAccountId().equals(accountId)) {
            throw new IllegalArgumentException("?????? ???????????? ???????????? ???????????????.");
        }
        return board;
    }
}
