package com.event.event.infrastructure.users.repository;

import com.event.event.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailContainsIgnoreCase(String email);
    boolean existsByReferralCode(String referralCode);
    Optional<User>  findByReferralCode(String referralCode);
}

