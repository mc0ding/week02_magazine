package com.spring.week02magazine.domain.dto.Account;

import com.spring.week02magazine.domain.dto.LikePost.LikePostAccountResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountResponseDto {
    private final Long account_id;
    private final String account_email;
    private final String account_name;
    private final List<LikePostAccountResponseDto> like_board;
    private final String token;

    @Builder
    public AccountResponseDto(Long account_id, String account_email, String account_name, String token, List<LikePostAccountResponseDto> like_board) {
        this.account_id = account_id;
        this.account_email = account_email;
        this.account_name = account_name;
        this.token = token;
        this.like_board = like_board;
    }

    public static AccountResponseDto of(Account account, String token) {
        List<LikePostAccountResponseDto> collect = account.getLikePostList().stream()
                .map(LikePostAccountResponseDto::new)
                .collect(Collectors.toList());

        return AccountResponseDto.builder()
                .account_id(account.getAccountId())
                .account_email(account.getAccountEmail())
                .account_name(account.getAccountName())
                .token(token)
                .like_board(collect)
                .build();
    }

}
