package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.entity.ClientEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findAll();

    @Query("SELECT c.id FROM ClientEntity c WHERE c.credential.username = :username")
    Long findClientIdByUsername(String username);

    @Query("SELECT COUNT(c)> 0 FROM ClientEntity c WHERE c.fio = :fio")
    boolean findClientByFio(String fio);

    @Query("SELECT c.fio FROM ClientEntity c WHERE c.credential.username = :username")
    String findFioByUsername(String username);


    @EntityGraph(attributePaths = {
            "credential", "pointOfInterest", "likedPoints",
            "route", "likedRoutes"
    })
    @Query("SELECT c FROM ClientEntity c WHERE c.credential.username = :username")
    Optional<ClientEntity> findClientWithRelations(String username);

    @Query("""
    SELECT c FROM ClientEntity c
    LEFT JOIN FETCH c.credential as credential
    LEFT JOIN FETCH c.pointOfInterest
    LEFT JOIN FETCH c.likedPoints
    LEFT JOIN FETCH c.route
    LEFT JOIN FETCH c.likedRoutes
    WHERE credential.username = :username
""")
    Optional<ClientEntity> findClientWithRelations1(String username);



}
