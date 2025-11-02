package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CredentialRepository extends JpaRepository<CredentialEntity,Long> {

    List<CredentialEntity> findAll();

    @Query("SELECT u FROM CredentialEntity u JOIN FETCH u.password WHERE u.username = :name")
    Optional<CredentialEntity> findByUsername(@Param("name") String name);

    @Query("SELECT COUNT(u.email) > 0 FROM CredentialEntity u WHERE u.email = :email")
    boolean isExistEmailByUsername(@Param("email") String email);
}