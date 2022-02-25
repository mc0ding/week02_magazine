package com.spring.week02magazine.domain.model;

import com.spring.week02magazine.domain.dto.Account.AccountResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccess {
    private String result;
    private String msg;
    private AccountResponseDto data;
}
