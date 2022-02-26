package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.repository.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
   private final AccountRepository accountRepository;

   public CustomUserDetailsService(AccountRepository accountRepository) {
      this.accountRepository = accountRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String accountEmail) { // 로그인 시, DB에서 유저정보와 권한정보를 가져온다.
      return accountRepository.findOneWithAuthoritiesByAccountEmail(accountEmail)           // ************* repository와 user 부분을 Account 로 바꾼다
         .map(account -> createUser(accountEmail, account))
         .orElseThrow(() -> new UsernameNotFoundException(accountEmail + " -> 데이터베이스에서 찾을 수 없습니다."));
   }

   private org.springframework.security.core.userdetails.User createUser(String accountEmail, Account account) {
      if (!account.isActivated()) { // 유저가 활성화 상태라면,
         throw new RuntimeException(accountEmail + " -> 활성화되어 있지 않습니다.");
      }
      List<GrantedAuthority> grantedAuthorities = account.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(account.getAccountEmail(),     // *********** User -> security User 일까?
              account.getPassword(), // 권한정보와 유저 네임, 패스워드를 가져온다.
              grantedAuthorities);
   }
}
