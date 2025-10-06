package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.AuthTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthTokenEntity, Long> {

    @Query("SELECT at FROM AuthTokenEntity at where at.refreshToken = :token")
    Optional<AuthTokenEntity> findByToken(@Param("token") String token);

    @Query("SELECT at FROM AuthTokenEntity at where at.credentialId = :credentialId")
    Optional<AuthTokenEntity> findByCredentialId(@Param("credentialId") Long credentialId);
}