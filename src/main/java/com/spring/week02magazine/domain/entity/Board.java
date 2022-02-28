package com.spring.week02magazine.domain.entity;

import com.spring.week02magazine.domain.model.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
public class Board extends Timestamped {

    protected Board () {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long boardId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String boardStatus;

    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

//    @BatchSize(size = 500)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<LikePost> likePostList = new ArrayList<>();

    public void insertAccountId(Account account) {
        this.account = account;
    }

    @Builder
    public Board(String content, String imgUrl, String boardStatus, Account account) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.boardStatus = boardStatus;
        this.account = account;
    }

    public void updateBoard(String content, String imgUrl, String boardStatus, Account account) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.boardStatus = boardStatus;
        this.account = account;
    }
}
