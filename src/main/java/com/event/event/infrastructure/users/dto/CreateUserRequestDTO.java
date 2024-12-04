package com.event.event.infrastructure.users.dto;


import com.event.event.entity.Role;
import com.event.event.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {
    private String email;
    private String password;
    private String name;

    public User toEntity(){
        User user= new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        return user;
    }

}
