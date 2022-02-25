package com.spring.week02magazine.domain.repository;

import com.spring.week02magazine.domain.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    LikePost findByAccount_IdAndBoard_Id(Long accountId, Long boardId);
}
