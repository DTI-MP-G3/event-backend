package com.event.event.infrastructure.users.controller;


import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.users.dto.CreateUserRequestDTO;
import com.event.event.usecase.user.CreateUserUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final CreateUserUsecase createUserUsecase;

    public UserController(CreateUserUsecase createUserUsecase) {
        this.createUserUsecase = createUserUsecase;
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser (@RequestBody CreateUserRequestDTO req){
        return ApiResponse.successfulResponse("Create New User Success",createUserUsecase.createUser(req));
    }

    @PostMapping("/registerEO")
    public ResponseEntity<?> createEventOrgUser (@RequestBody CreateUserRequestDTO req){
        return ApiResponse.successfulResponse("Create New User Success",createUserUsecase.createEventOrgUser(req));
    }

}
