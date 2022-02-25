package com.spring.week02magazine.domain.dto.Account;

import com.spring.week02magazine.domain.dto.LikePost.LikePostAccountResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountResponseDto {
    private final Long accountId;
    private final String accountEmail;
    private final String accountName;
    private final List<LikePostAccountResponseDto> likeBoard;
    private final String token;

    @Builder
    public AccountResponseDto(Long accountId, String accountEmail, String accountName, String token, List<LikePostAccountResponseDto> likeBoard) {
        this.accountId = accountId;
        this.accountEmail = accountEmail;
        this.accountName = accountName;
        this.token = token;
        this.likeBoard = likeBoard;
    }
//    public void setToken(String token) { this.token = token; }

    public static AccountResponseDto of(Account account, String username, String token) {
        List<LikePostAccountResponseDto> collect = account.getLikePostList().stream()
                .map(LikePostAccountResponseDto::new)
                .collect(Collectors.toList());

        return AccountResponseDto.builder()
                .accountId(account.getId())
                .accountEmail(account.getEmail())
                .accountName(username)
                .token(token)
                .likeBoard(collect)
                .build();
    }

}
