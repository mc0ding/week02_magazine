package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Board.BoardIdResponseDto;
import com.spring.week02magazine.domain.dto.Board.BoardRequestDto;
import com.spring.week02magazine.domain.dto.Board.BoardResponseDto;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.repository.BoardRepository;
import com.spring.week02magazine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> listBoard() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        return boardList.stream()
                .map(BoardResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long creatBoard(BoardRequestDto requestDto, UserDetailsImpl accountDetails) {
        if (accountDetails.getUser() == null) { throw new IllegalArgumentException("로그인이 필요합니다."); }
        return new BoardIdResponseDto(boardRepository.save(BoardRequestDto.toEntity(requestDto)).getId()).getBoardId();
    }

    @Transactional
    public void updateBoard(Long boardId, BoardRequestDto requestDto, UserDetailsImpl accountDetails) {
        if (accountDetails.getUser() == null) { throw new IllegalArgumentException("로그인이 필요합니다."); }
        Board board = boardValidation(boardId, accountDetails.getUser().getId());
        board.updateBoard(requestDto.getContent(), requestDto.getImgUrl(), requestDto.getLayout());
    }

    @Transactional
    public void deleteBoard(Long boardId, UserDetailsImpl accountDetails) {
        if (accountDetails.getUser() == null) { throw new IllegalArgumentException("로그인이 필요합니다."); }
        boardRepository.delete(boardValidation(boardId, accountDetails.getUser().getId()));
    }

    private Board boardValidation(Long boardId, Long accountId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        if (board.getAccountId().equals(accountId)) {
            throw new IllegalArgumentException("해당 게시글을 작성하지 않았습니다.");
        }
        return board;
    }
}
