package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Account.AccountLoginRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.repository.AccountRepository;
import com.spring.week02magazine.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void register(AccountRegisterRequestDto requestDto) {
        registerValidation(requestDto);
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.encodedPassword(password);
        accountRepository.save(AccountRegisterRequestDto.toEntity(requestDto));
    }

    @Transactional
    public AccountResponseDto login(AccountLoginRequestDto requestDto) {
        Account account = accountRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다.")
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        String username = accountRepository.findByEmail(requestDto.getEmail()).get().getUsername();
        String token = jwtTokenProvider.createToken(account.getEmail());
//        AccountResponseDto responseDto = accountRepository.login(requestDto);
//        responseDto.setToken(token);

        return AccountResponseDto.of(account, username, token);
    }

    private void registerValidation(AccountRegisterRequestDto requestDto) {
        Account account = accountRepository.findByEmail(requestDto.getEmail()).orElse(null);
        if (account != null) {
            throw new IllegalArgumentException("해당 이메일 주소는 사용 중입니다.");
        }
        Account username = accountRepository.findByUsername(requestDto.getUsername()).orElse(null);
        if (username != null) {
            throw new IllegalArgumentException("해당 닉네임은 사용 중입니다.");
        }
        if (!Pattern.matches("^[A-Za-z0-9]{3,}$", requestDto.getUsername())){
            throw new IllegalArgumentException("닉네임을 3자 이상으로 설정하지 않으셨거나 사용할 수 없는 문자가 포함되었습니다.");
        } else if (requestDto.getPassword().length() <= 3) {
            throw new IllegalArgumentException("비밀번호는 4자 이상으로 설정해주세요.");
        } else if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())){
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        } else if (requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되었습니다.");
        }
    }
}