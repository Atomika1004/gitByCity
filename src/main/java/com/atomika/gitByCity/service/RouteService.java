package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.ResponseForCreateOrUpdate;
import com.atomika.gitByCity.dto.Route;

import com.atomika.gitByCity.dto.mapper.RouteMapper;

import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import com.atomika.gitByCity.exception.NotFoundException;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final ClientRepository clientRepository;

    @Transactional
    public ResponseForCreateOrUpdate create(Route route, String clientName) {
        RouteEntity isExists = routeRepository.findRouteEntityByName(route.getName());
        if (isExists != null) {
            return ResponseForCreateOrUpdate.builder().message("Маршрут с таким названием уже существует").
                    success(false).build();
        }else {
            route.setClientId(clientRepository.findClientIdByUsername(clientName));
            routeMapper.entityToDto(routeRepository.save(routeMapper.dtoToEntity(route)));
            return ResponseForCreateOrUpdate.builder().message("Маршрут успешно создан, поздравляю!!!").
                    success(true).build();
        }
    }

    @Transactional
    public ResponseForCreateOrUpdate update(Route route) {
        RouteEntity isExists = routeRepository.findRouteEntityByName(route.getName());
        if (isExists != null && !isExists.getId().equals(route.getId())) {
            return ResponseForCreateOrUpdate.builder().message("Маршрут с таким названием уже существует").
                    success(false).build();
        }else {
            routeMapper.entityToDto(routeRepository.save(routeMapper.dtoToEntity(route)));
            return ResponseForCreateOrUpdate.builder().message("Маршрут успешно сохранен, поздравляю!!!").
                    success(true).build();
        }
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

    public Long addLike(Long routeId, String clientName) {
        RouteEntity routeEntity = routeRepository.findById(routeId).orElse(null);
        Long clientId = clientRepository.findClientIdByUsername(clientName);
        if (clientId == null) {
            throw new  NotFoundException("Пользователь не найден");
        }
        ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);
        if (routeEntity == null) {
            throw new NotFoundException("Маршрут не найден");
        }
        else {
            boolean isCreator = clientEntity.getLikedRoutes().stream().
                    anyMatch(point -> Objects.equals(point.getId(), routeId));
            if (isCreator) {
                deleteLike(routeId, clientId);
            }
            else {
                clientEntity.getLikedRoutes().add(routeEntity);
                routeEntity.getLikes().add(clientEntity);
                clientRepository.save(clientEntity);
            }
            List<ClientEntity> list = routeEntity.getLikes();
            if(!list.isEmpty()) {
                return (long) list.size();
            }
            else {
                return null;
            }
        }
    }

    public void deleteLike(Long routeId, Long clientId) {
        RouteEntity routeEntity = routeRepository.findById(routeId).orElse(null);
        ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);
        if (routeEntity == null || clientEntity == null) {
            return;
        }
        else {
            clientEntity.getLikedRoutes().remove(routeEntity);
            routeEntity.getLikes().remove(clientEntity);
            clientRepository.save(clientEntity);
        }
    }

    public void updatePosition (Long routeId) {
        Route route = routeMapper.entityToDto(routeRepository.findById(routeId).orElseThrow(EntityNotFoundException::new));
        routeMapper.entityToDto(routeRepository.save(routeMapper.dtoToEntity(route)));
    }

    public boolean isCreator (String username, long routeId) {
        return routeRepository.isCreator(username, routeId);
    }
}
