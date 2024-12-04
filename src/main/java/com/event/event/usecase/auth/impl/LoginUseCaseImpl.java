package com.event.event.usecase.auth.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.infrastructure.security.TokenService;
import com.event.event.infrastructure.users.dto.LoginRequestDTO;
import com.event.event.infrastructure.users.dto.LoginResponseDTO;
import com.event.event.usecase.auth.LoginUseCase;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Log
@Service
public class LoginUseCaseImpl implements LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginUseCaseImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public LoginResponseDTO authenticateUser(LoginRequestDTO req) {
        try{
            log.info("Login with");
            log.info(req.getEmail());
            log.info(req.getPassword());
            Authentication authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
            String token = tokenService.generateToken(authentication);
            return new LoginResponseDTO(token);
        }catch (AuthenticationException e){
            throw new DataNotFoundException("Wrong Credentials");
        }
    }
}
