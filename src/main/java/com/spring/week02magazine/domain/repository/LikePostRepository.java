package com.spring.week02magazine.domain.repository;

import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findByAccountAndBoard(Account account, Board board);
    List<LikePost> findLikePostByBoard(Board board);
}
