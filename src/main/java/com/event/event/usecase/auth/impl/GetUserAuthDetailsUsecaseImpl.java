package com.event.event.usecase.auth.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.entity.User;
import com.event.event.infrastructure.users.dto.UserAuth;
import com.event.event.infrastructure.users.repository.UsersRepository;
import com.event.event.usecase.auth.GetUserAuthDetailsUsecase;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log
@Service
public class GetUserAuthDetailsUsecaseImpl implements GetUserAuthDetailsUsecase {
    private final UsersRepository usersRepository;

    public GetUserAuthDetailsUsecaseImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existingUser = usersRepository.findByEmailContainsIgnoreCase(username).orElseThrow(()-> new DataNotFoundException("User not found with email: " + username));
        log.info(existingUser.getEmail());
        log.info(existingUser.getPassword());

        UserAuth userAuth = new UserAuth();
        userAuth.setUser(existingUser);
        return userAuth;
    }
}
