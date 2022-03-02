package com.spring.week02magazine.domain.repository;

import com.spring.week02magazine.domain.dto.Account.AccountRegisterRequestDto;
import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Authority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Transactional
class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Test
    @DisplayName("계정 저장 확인")
    void saveAccount() {
        // given
        Account account = new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("test@test.com", "123", "123", "nick", new Authority("ROLE_USER"), true));
        // when
        Account savedAccount = repository.save(account);
        // then
        assertThat(account).isSameAs(savedAccount);
        assertThat(savedAccount.getAccountId()).isNotNull();
        assertThat(account.getAccountEmail()).isEqualTo(savedAccount.getAccountEmail());
        assertThat(account.getAccountName()).isEqualTo(savedAccount.getAccountName());
        assertThat(repository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("저장된 계정 확인")
    void findAccount() {
        // given
        Account savedAccount = repository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("findtest@test.com", "123", "123", "nickname", new Authority("ROLE_USER"), true)));
        Account savedAccount2 = repository.save(new AccountRegisterRequestDto().toEntity(new AccountRegisterRequestDto("222@google.com", "321", "321", "haha", new Authority("ROLE_USER"), true)));
        // when
        Account findAccount = repository.findByAccountId(savedAccount.getAccountId()).orElseThrow(() -> new IllegalArgumentException("잘못된 값 입니다 : " + savedAccount.getAccountId()));
        Account findAccount2 = repository.findByAccountId(savedAccount2.getAccountId()).orElseThrow(() -> new IllegalArgumentException("잘못된 값 입니다 : " + savedAccount2.getAccountId()));
        // then
        assertThat(repository.count()).isEqualTo(4);
        assertThat(findAccount.getAccountEmail()).isEqualTo("findtest@test.com");
        assertThat(findAccount.getAccountName()).isEqualTo("nickname");
        assertThat(findAccount2.getAccountEmail()).isEqualTo("222@google.com");
        assertThat(findAccount2.getAccountName()).isEqualTo("haha");
    }
}