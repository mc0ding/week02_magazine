package com.spring.week02magazine.domain.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "account")
public class Account {

    protected Account () {}

    @Id
    @Column(name = "accountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "accountEmail", unique = true)
    private String accountEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "accountName", unique = true)
    private String accountName;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "accountId", referencedColumnName = "accountId")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<LikePost> likePostList = new ArrayList<>();

    @Builder
    public Account(String accountEmail, String password, String accountName, boolean activated, Set<Authority> authorities) {
        this.accountEmail = accountEmail;
        this.password = password;
        this.accountName = accountName;
        this.activated = activated;
        this.authorities = authorities;
    }
}
