//package com.spring.week02magazine.domain.repository;
//
//import com.spring.week02magazine.domain.entity.User;
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//   @EntityGraph(attributePaths = "authorities") // @EntityGraph는 쿼리가 수행될 때 Eager조회로 authorities 정보를 같이 가져오게 된다.
//   Optional<User> findOneWithAuthoritiesByUsername(String username);
//}
