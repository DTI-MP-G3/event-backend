package com.event.event.usecase.user.impl;

import com.event.event.entity.User;
import com.event.event.infrastructure.users.repository.UsersRepository;
import com.event.event.usecase.user.SearchUserUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SearchUserUsecaseImpl implements SearchUserUsecase {

    UsersRepository usersRepository;

    public SearchUserUsecaseImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<User> findUserbyId(Long id){
        return usersRepository.findById(id);
    }
}
