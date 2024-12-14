package com.event.event.usecase.user;

import com.event.event.entity.User;

import java.util.Optional;

public interface SearchUserUsecase {

    Optional<User> findUserbyId(Long id);
}
