package com.atomika.gitByCity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "password")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String password;

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PasswordEntity(String password) {
        this.password = passwordEncoder.encode(password);
    }

    @JsonIgnore
    void setPasswordWithEncoder(String password) {
        this.password = passwordEncoder.encode(password);
    }
}
