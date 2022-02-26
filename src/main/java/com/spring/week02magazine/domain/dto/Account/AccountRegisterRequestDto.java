package com.spring.week02magazine.domain.dto.Account;

import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class AccountRegisterRequestDto {
    private String account_email;
    private String password;
    private String password_check;
    private String account_name;
    private Authority authority;
    private boolean activated;

    public void encodedPassword(String password) {
        this.password = password;
    }

    @Builder
    public AccountRegisterRequestDto(String account_email, String password, String password_check, String account_name, Authority authority, boolean activated) {
        this.account_email = account_email;
        this.password = password;
        this.password_check = password_check;
        this.account_name = account_name;
        this.authority = authority;
        this.activated = activated;
    }

    public static Account toEntity(AccountRegisterRequestDto requestDto) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        return Account.builder()
                .accountEmail(requestDto.getAccount_email())
                .password(requestDto.getPassword())
                .accountName(requestDto.getAccount_name())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
    }
}
