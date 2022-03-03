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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest_Mock_failed {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("register 기능 동작 확인")
    void register() {
        // 실제 AccountService에서 password가 encode 되는 부분을 주석처리하고 실행 시 가능
        // password를 encode 해서 test 하려니 주입 받아야 할 것이 많고 코드가 더욱 길어져서 제외하고 테스트함

        // given
        AccountRegisterRequestDto requestDto = new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true);
        when(accountRepository.save(any())).thenReturn(requestDto.toEntity(requestDto));
        AccountRegisterRequestDto requestDto2 = new AccountRegisterRequestDto("test2@test.com", "1234", "1234", "nickname2", new Authority("ROLE_USER"), true);
        when(accountRepository.save(any())).thenReturn(requestDto2.toEntity(requestDto2));
        // when
        accountService.register(requestDto);
        accountService.register(requestDto2);
        // then
        // Id 생성 전략으로 Identity를 사용하므로, 실제 DB에 저장되야만 Id가 생성된다. 따라서 테스트에서 Id를 검증할 수 없다.
        // 만약 Id를 검증하려면 Repository를 Mock이 아니라 실제 Bean으로 사용해야 한다.
    }

    @Test
    @DisplayName("login 기능 동작 확인")
    void login() {
        // 실제 AccountService에서 password가 encode 되는 부분을 주석처리, authentication 생성하고 token을 생성하는 부분까지 전부 주입 받아야 함
        // 현재 전체 코드가 작성되어 Mock으로 진행하는 테스트는 중단, SpringBootTest로 다시 테스트함

        // given
        Account account = new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true));
        AccountRegisterRequestDto requestDto = new AccountRegisterRequestDto("test@test.com", "1234", "1234", "nickname", new Authority("ROLE_USER"), true);
        when(accountRepository.save(any())).thenReturn(requestDto.toEntity(requestDto));
        accountService.register(requestDto);
        when(accountRepository.findByAccountEmail(requestDto.getAccount_email())).thenReturn(Optional.of(requestDto.toEntity(requestDto)));
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