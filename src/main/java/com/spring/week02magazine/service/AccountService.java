package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Account.AccountLoginRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.repository.AccountRepository;
import com.spring.week02magazine.security.jwt.JwtFilter;
import com.spring.week02magazine.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void register(AccountRegisterRequestDto requestDto) {
        registerValidation(requestDto);
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.encodedPassword(password);
        accountRepository.save(AccountRegisterRequestDto.toEntity(requestDto));
    }

    @Transactional
    public AccountResponseDto login(AccountLoginRequestDto requestDto) {
        Account account = accountRepository.findByAccountEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다.")
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);
        return AccountResponseDto.of(account, token);
    }

    private void registerValidation(AccountRegisterRequestDto requestDto) {
        Account account = accountRepository.findByAccountEmail(requestDto.getAccount_email()).orElse(null);
        if (account != null) {
            throw new IllegalArgumentException("해당 이메일 주소는 사용 중입니다.");
        }
        Account username = accountRepository.findByAccountName(requestDto.getAccount_name()).orElse(null);
        if (username != null) {
            throw new IllegalArgumentException("해당 닉네임은 사용 중입니다.");
        }
        if (!Pattern.matches("^[A-Za-z0-9]{3,}$", requestDto.getAccount_name())){
            throw new IllegalArgumentException("닉네임을 3자 이상으로 설정하지 않으셨거나 사용할 수 없는 문자가 포함되었습니다.");
        } else if (requestDto.getPassword().length() <= 3) {
            throw new IllegalArgumentException("비밀번호는 4자 이상으로 설정해주세요.");
        } else if (!requestDto.getPassword().equals(requestDto.getPassword_check())){
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        } else if (requestDto.getPassword().contains(requestDto.getAccount_name())) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되었습니다.");
        }
    }
}