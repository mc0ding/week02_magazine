package com.spring.week02magazine.domain.dto.Account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountLoginRequestDto {
    private String email;
    private String password;

    public void encodedPassword(String password) {
        this.password = password;
    }

    @Builder
    public AccountLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
