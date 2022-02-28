package com.spring.week02magazine.domain.dto;

import com.spring.week02magazine.domain.entity.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AccountDetailsDto {
   private Long account_id;
   private String account_email;
   private String password;
   private String account_name;
   private Set<AuthorityDto> authorityDtoSet;

   @Builder
   public AccountDetailsDto(Long account_id, String account_email, String password, String account_name, Set<AuthorityDto> authorityDtoSet) {
      this.account_id = account_id;
      this.account_email = account_email;
      this.password = password;
      this.account_name = account_name;
      this.authorityDtoSet = authorityDtoSet;
   }

   public static AccountDetailsDto from(Account account) {
      if(account == null) return null;
      return AccountDetailsDto.builder()
              .account_id(account.getAccountId())
              .account_email(account.getAccountEmail())
              .account_name(account.getAccountName())
              .authorityDtoSet(account.getAuthorities().stream()
                      .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                      .collect(Collectors.toSet()))
              .build();
   }
}