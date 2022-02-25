package com.spring.week02magazine.domain.entity;

import com.spring.week02magazine.domain.model.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String layout;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account accountId;

//    @BatchSize(size = 500)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<LikePost> likePostList = new ArrayList<>();

    @Builder
    public Board(String content, String imgUrl, String layout) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.layout = layout;
    }

    public void updateBoard(String content, String imgUrl, String layout) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.layout = layout;
    }
}
