package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    List<ClientEntity> findAll();

    ClientEntity findById(long id);

    @Query("SELECT c.id FROM ClientEntity c WHERE c.credential.username = :username")
    Long findClientIdByUsername(String username);

    //List<ClientEntity> findClientEntitiesByRouteId(Long routeId);
}
