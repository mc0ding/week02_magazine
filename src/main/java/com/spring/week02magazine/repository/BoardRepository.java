package com.spring.week02magazine.repository;

import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc();
    Optional<Board> findByBoardId(Long boardId);
}
