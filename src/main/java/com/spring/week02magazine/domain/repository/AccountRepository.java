package com.spring.week02magazine.domain.repository;

import com.spring.week02magazine.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsername(String username);

//    AccountResponseDto login(AccountRequestDto requestDto);
}
