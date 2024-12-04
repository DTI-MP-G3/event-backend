package com.event.event.infrastructure.users.repository;

import com.event.event.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer > {
    Optional<Role> findByName(String name);
}
