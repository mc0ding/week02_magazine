package com.spring.week02magazine.domain.dto.Account;

import com.spring.week02magazine.domain.entity.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountRegisterRequestDto {
    private String email;
    private String password;
    private String passwordCheck;
    private String username;

    public void encodedPassword(String password) {
        this.password = password;
    }

    @Builder
    public AccountRegisterRequestDto(String email, String password, String passwordCheck, String username) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.username = username;
    }

    public static Account toEntity(AccountRegisterRequestDto requestDto) {
        return Account.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .username(requestDto.getUsername())
                .build();
    }
}
