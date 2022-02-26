//package com.spring.week02magazine.controller;
//
//import com.spring.week02magazine.domain.dto.AccountDetailsDto;
//import com.spring.week02magazine.service.AccountDetailsService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//    private final AccountDetailsService accountDetailsService;
//
//    public UserController(AccountDetailsService accountDetailsService) {
//        this.accountDetailsService = accountDetailsService;
//    }
//
//    @GetMapping("/hello")
//    public ResponseEntity<String> hello() {
//        return ResponseEntity.ok("hello");
//    }
//
//    @PostMapping("/test-redirect")
//    public void testRedirect(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/api/user");
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<AccountDetailsDto> signup(
//            @Valid @RequestBody AccountDetailsDto accountDetailsDto
//    ) {
//        return ResponseEntity.ok(accountDetailsService.signup(accountDetailsDto));
//    }
//
//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<AccountDetailsDto> getMyUserInfo(HttpServletRequest request) {
//        return ResponseEntity.ok(accountDetailsService.getMyUserWithAuthorities());
//    }
//
//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<AccountDetailsDto> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(accountDetailsService.getUserWithAuthorities(username));
//    }
//}
