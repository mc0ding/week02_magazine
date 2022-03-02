package com.spring.week02magazine.repository;

import com.spring.week02magazine.domain.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(Long accountId);
    Optional<Account> findByAccountEmail(String accountEmail);
    Optional<Account> findByAccountName(String accountName);

    @EntityGraph(attributePaths = "authorities") // @EntityGraph는 쿼리가 수행될 때 Eager조회로 authorities 정보를 같이 가져오게 된다.
    Optional<Account> findOneWithAuthoritiesByAccountEmail(String accountEmail);
//    AccountResponseDto login(AccountRequestDto requestDto);
}
