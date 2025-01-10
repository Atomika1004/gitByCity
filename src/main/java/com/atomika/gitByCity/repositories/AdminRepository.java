package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
     List<AdminEntity> findAll();
}
