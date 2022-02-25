package com.spring.week02magazine.controller;

import com.spring.week02magazine.domain.dto.Account.AccountLoginRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountResponseDto;
import com.spring.week02magazine.domain.model.LoginSuccess;
import com.spring.week02magazine.domain.model.Success;
import com.spring.week02magazine.security.UserDetailsImpl;
import com.spring.week02magazine.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Success> register(@RequestBody AccountRegisterRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl accountDetails) {
        if (accountDetails != null) {
            throw new IllegalArgumentException("이미 로그인이 되어있습니다.");
        }
        accountService.register(requestDto);
        return new ResponseEntity<>(new Success("success", "회원가입이 완료되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccess> login(@RequestBody AccountLoginRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl accountDetails) {
        if (accountDetails != null) {
            throw new IllegalArgumentException("이미 로그인이 되어있습니다.");
        }
        AccountResponseDto responseDto = accountService.login(requestDto);
        return new ResponseEntity<>(new LoginSuccess("success", "로그인에 성공하셨습니다.", responseDto), HttpStatus.OK);
    }
}