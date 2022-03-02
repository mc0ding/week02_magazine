package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.Account.AccountLoginRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.dto.Account.AccountResponseDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import com.spring.week02magazine.domain.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("register 기능 동작 확인")
    <Base64Encoder>
    void register() {
        // given
        AccountRegisterRequestDto requestDto = new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true);
        when(accountRepository.save(any())).thenReturn(requestDto.toEntity(new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true)));
        AccountRegisterRequestDto requestDto2 = new AccountRegisterRequestDto("test2@test.com", "1234", "1234", "nickname2", new Authority("ROLE_USER"), true);
        when(accountRepository.save(any())).thenReturn(requestDto2.toEntity(new AccountRegisterRequestDto("test2@test.com", "1234", "1234", "nickname2", new Authority("ROLE_USER"), true)));
//        Base64Encoder passwordEncoder;
//        String password = passwordEncoder.encode(requestDto.getPassword());
        // when
//        requestDto.encodedPassword(password);
        accountService.register(requestDto);
        accountService.register(requestDto2);
        // then
        // Id 생성 전략으로 Identity를 사용하므로, 실제 DB에 저장되야만 Id가 생성된다. 따라서 테스트에서 Id를 검증할 수 없다.
        // 만약 Id를 검증하려면 Repository를 Mock이 아니라 실제 Bean으로 사용해야 한다.
    }

    @Test
    @DisplayName("register 기능 동작 확인")
    void login() {
        // given
        Account account = new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true));
        AccountRegisterRequestDto requestDto = new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true);
        when(accountRepository.save(any())).thenReturn(requestDto.toEntity(new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true)));
        accountService.register(requestDto);
        when(accountRepository.findByAccountId(3L)).thenReturn(Optional.of(requestDto.toEntity(new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true))));
        // when
        AccountLoginRequestDto loginRequestDto = new AccountLoginRequestDto(requestDto.getAccount_email(), requestDto.getPassword());

        AccountResponseDto responseDto = accountService.login(loginRequestDto).of(account, "abcdefg1234567");
        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getAccount_email()).isEqualTo("test@test.com");
        assertThat(responseDto.getAccount_name()).isEqualTo("nickname");
        assertThat(responseDto.getToken()).isEqualTo("abcdefg1234567");
    }
}