package com.event.event.infrastructure.users.dto;


import com.event.event.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        var authorities = new ArrayList<GrantedAuthority>();

        this.user.getRoles().forEach(role-> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }

    @Override
    public String getUsername(){
        return this.user.getEmail();
    }

    @Override
    public String getPassword(){
        return this.user.getPassword();
    }


    @Override
    public boolean isAccountNonExpired(){
        return true;
    }


    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

}
