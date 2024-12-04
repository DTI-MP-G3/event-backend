package com.event.event.usecase.auth;

import com.event.event.infrastructure.users.dto.LoginRequestDTO;
import com.event.event.infrastructure.users.dto.LoginResponseDTO;

public interface LoginUseCase {
    LoginResponseDTO authenticateUser(LoginRequestDTO req);
}
