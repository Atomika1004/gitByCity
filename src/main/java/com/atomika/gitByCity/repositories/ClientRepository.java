package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    List<ClientEntity> findAll();

    @Query("SELECT c.id FROM ClientEntity c WHERE c.credential.username = :username")
    Long findClientIdByUsername(String username);

    @Query("SELECT COUNT(c)> 0 FROM ClientEntity c WHERE c.fio = :fio")
    boolean findClientByFio(String fio);

    @Query("SELECT c.fio FROM ClientEntity c WHERE c.credential.username = :username")
    String findFioByUsername(String username);

}
