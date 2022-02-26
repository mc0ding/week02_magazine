package com.spring.week02magazine.service;

import com.spring.week02magazine.domain.dto.AccountDetailsDto;
import com.spring.week02magazine.domain.repository.AccountRepository;
import com.spring.week02magazine.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountDetailsService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public AccountDetailsDto getMyUserWithAuthorities() {
        return AccountDetailsDto.from(SecurityUtil.getCurrentUsername().flatMap(accountRepository::findOneWithAuthoritiesByAccountEmail).orElse(null));
    }
}
