package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Route;

import com.atomika.gitByCity.dto.mapper.RouteMapper;

import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final ClientRepository clientRepository;

    @Transactional
    public Route create(Route route) {
       return routeMapper.entityToDto(routeRepository.save(routeMapper.dtoToEntity(route)));
    }

    @Transactional
    public Route update(Route route) {
        return routeMapper.entityToDto(routeRepository.save(routeMapper.dtoToEntity(route)));
    }

    @Transactional
    public Long delete (Long id) {
        routeRepository.deleteById(id);
        return id;
    }

    @Transactional
    public Route findById(Long id) {
        return routeMapper.entityToDto(routeRepository.findById(id).orElse(null));
    }


    @Transactional
    public List<Route> findAll() {
        return routeMapper.toList(routeRepository.findAll());
    }

    //todo уточнить про комментарии
    @Transactional
    public Long addLike(Long routeId, Long clientId) {
        RouteEntity routeEntity = routeRepository.findById(routeId).orElse(null);
        ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);
        if (routeEntity == null || clientEntity == null) {
            return null;
        }
        else {
            clientEntity.getLikedRoutes().add(routeEntity);
            routeEntity.getLikes().add(clientEntity);
            clientRepository.save(clientEntity);

            List<ClientEntity> list = routeEntity.getLikes();
            if(!list.isEmpty()) {
                return (long) list.size();
            }
            else {
                return null;
            }
        }
    }

    @Transactional
    public Long deleteLike(Long routeId, Long clientId) {
        RouteEntity routeEntity = routeRepository.findById(routeId).orElse(null);
        ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);
        if (routeEntity == null || clientEntity == null) {
            return null;
        }
        else {
            clientEntity.getLikedRoutes().remove(routeEntity);
            routeEntity.getLikes().remove(clientEntity);
            clientRepository.save(clientEntity);

            List<ClientEntity> list = routeEntity.getLikes();
            if(!list.isEmpty()) {
                return (long) list.size();
            }
            else {
                return null;
            }
        }
    }

    public void updatePosition (Long routeId) {
        Route route = routeMapper.entityToDto(routeRepository.findById(routeId).orElseThrow(EntityNotFoundException::new));
        routeMapper.entityToDto(routeRepository.save(routeMapper.dtoToEntity(route)));
    }
}
