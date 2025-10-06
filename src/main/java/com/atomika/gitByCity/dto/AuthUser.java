package com.atomika.gitByCity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthUser implements UserDetails {

    String username;
    String password;
    boolean enabled;
    Long credentialId;
    Role role;

    private List<SimpleGrantedAuthority> authorities;
}
