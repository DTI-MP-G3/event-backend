package com.event.event.usecase.user;

import com.event.event.entity.User;
import com.event.event.infrastructure.users.dto.CreateUserRequestDTO;

public interface CreateUserUsecase {
    User createUser(CreateUserRequestDTO req);
    User createEventOrgUser(CreateUserRequestDTO req);
}
