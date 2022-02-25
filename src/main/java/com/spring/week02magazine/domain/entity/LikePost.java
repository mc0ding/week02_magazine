package com.spring.week02magazine.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class LikePost {

    @Id
    @Column(name = "likeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @Builder
    public LikePost(Account account, Board board) {
        this.account = account;
        this.board = board;
    }

    public void changeAccount(Account account) {
        this.account = account;
        account.getLikePostList().add(this);
    }
    public void changeBoard(Board board) {
        this.board = board;
        board.getLikePostList().add(this);
    }
}
