package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    List<CommentEntity> findAll();

    @Query("SELECT COUNT(c) >0  from CommentEntity c WHERE c.client.credential.username = :username AND c.id = :commentId")
    boolean isCreator(String username, long commentId);
}
