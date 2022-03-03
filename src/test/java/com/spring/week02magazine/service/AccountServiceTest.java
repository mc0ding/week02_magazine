package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Account.AccountLoginRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import com.spring.week02magazine.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("register 기능 동작 확인")
    void register() {
        // given
        AccountRegisterRequestDto requestDto = new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true);
        AccountRegisterRequestDto requestDto2 = new AccountRegisterRequestDto("test2@test.com", "1234", "1234", "nickname2", new Authority("ROLE_USER"), true);
        // when
        accountService.register(requestDto);
        accountService.register(requestDto2);
        // then
        Account test = accountRepository.findByAccountEmail(requestDto.getAccount_email()).get();
        Account test2 = accountRepository.findByAccountEmail(requestDto2.getAccount_email()).get();
        assertThat(test).isNotNull();
        assertThat(test.getAccountEmail()).isEqualTo("test@test.com");
        assertThat(test.getAccountName()).isEqualTo("nickname");
        assertThat(test2).isNotNull();
        assertThat(test2.getAccountEmail()).isEqualTo("test2@test.com");
        assertThat(test2.getAccountName()).isEqualTo("nickname2");
    }

    @Test
    @DisplayName("login 기능 동작 확인")
    void login() {
        // given
        Account account = new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true));
        AccountRegisterRequestDto requestDto = new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true);
        accountService.register(requestDto);
        // when
        AccountLoginRequestDto loginRequestDto = new AccountLoginRequestDto("test@test.com", "1234");
        AccountResponseDto responseDto = accountService.login(loginRequestDto).of(account, "abcdefg1234567");
        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getAccount_email()).isEqualTo("test@test.com");
        assertThat(responseDto.getAccount_name()).isEqualTo("nickname");
        assertThat(responseDto.getToken()).isEqualTo("abcdefg1234567");
    }
}