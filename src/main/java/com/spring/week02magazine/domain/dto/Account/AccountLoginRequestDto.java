package com.spring.week02magazine.domain.dto.Account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountLoginRequestDto {
    private String account_email;
    private String password;

    public void encodedPassword(String password) {
        this.password = password;
    }

    @Builder
    public AccountLoginRequestDto(String account_email, String password) {
        this.account_email = account_email;
        this.password = password;
    }
}
