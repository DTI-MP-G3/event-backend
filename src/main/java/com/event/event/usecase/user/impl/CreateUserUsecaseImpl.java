package com.event.event.usecase.user.impl;

import com.event.event.entity.Role;
import com.event.event.entity.User;
import com.event.event.infrastructure.users.dto.CreateUserRequestDTO;
import com.event.event.infrastructure.users.repository.RoleRepository;
import com.event.event.infrastructure.users.repository.UsersRepository;
import com.event.event.usecase.user.CreateUserUsecase;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@Service
public class CreateUserUsecaseImpl implements CreateUserUsecase {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public CreateUserUsecaseImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(CreateUserRequestDTO req) {
        User newUser = req.toEntity();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Optional<Role> defaultRole = roleRepository.findByName("USER");
        if (defaultRole.isPresent()) {
            newUser.getRoles().add(defaultRole.get());
        } else {
            throw new RuntimeException("Default role not found");
        }

        try {
            usersRepository.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }

    @Override
    public User createEventOrgUser(CreateUserRequestDTO req) {
        User newUser = req.toEntity();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Optional<Role> defaultRole = roleRepository.findByName("EVENT_ORGANIZER");
        if (defaultRole.isPresent()) {
            newUser.getRoles().add(defaultRole.get());
        } else {
            throw new RuntimeException("Default role not found");
        }

        try {
            usersRepository.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }

}
