package com.event.event.infrastructure.users.controller;

import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.users.dto.LoginRequestDTO;
import com.event.event.usecase.auth.LoginUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDTO req){
        return ApiResponse.successfulResponse("Login success", loginUseCase.authenticateUser(req));
    }


}
