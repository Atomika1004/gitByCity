package com.atomika.gitByCity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "passwords")
@Getter
@Setter
@NoArgsConstructor
public class PasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PasswordEntity(String password) {
        this.password = passwordEncoder.encode(password);
    }

    @JsonIgnore
    private void setPasswordWithEncoder (String password) {
        this.password = passwordEncoder.encode(password);
    }
}
