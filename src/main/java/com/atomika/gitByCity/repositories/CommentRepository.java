package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {

    @Query("SELECT c FROM CommentEntity c WHERE c.routeId = :routeId")
    List<CommentEntity> findAllForRoute(long routeId);

    @Query("SELECT c FROM CommentEntity c WHERE c.pointOfInterestId = :pointId")
    List<CommentEntity> findAllForPoint(long pointId);

    @Query("SELECT COUNT(c) > 0  from CommentEntity c WHERE c.client.credential.username = :username AND c.id = :commentId")
    boolean isCreator(String username, long commentId);
}