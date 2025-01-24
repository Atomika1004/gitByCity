package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.dto.PointOfInterestRoute;
import com.atomika.gitByCity.dto.ResponseForCreateOrUpdate;
import com.atomika.gitByCity.dto.mapper.PointOfInterestMapper;
import com.atomika.gitByCity.dto.mapper.PointOfInterestRouteMapper;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointOfInterestService {

    private final PointOfInterestRepository pointOfInterestRepository;
    private final PointOfInterestMapper pointOfInterestMapper;
    private final ClientRepository clientRepository;
    private final PointOfInterestRouteRepository pointOfInterestRouteRepository;
    private final PointOfInterestRouteMapper pointOfInterestRouteMapper;
    private final RouteService routeService;


    public ResponseForCreateOrUpdate create (PointOfInterest pointOfInterest, String clientName) {
        PointOfInterestEntity isExists = pointOfInterestRepository.findPointOfInterestEntityByName(pointOfInterest.getName());
        if (isExists != null) {
            return ResponseForCreateOrUpdate.builder().message("Точка с таким названием уже существует").
                    success(false).build();
        }else {
            pointOfInterest.setClientId(clientRepository.findClientIdByUsername(clientName));
            pointOfInterestMapper.entityToDto(
                    pointOfInterestRepository.save(pointOfInterestMapper.dtoToEntity(pointOfInterest)));
            return ResponseForCreateOrUpdate.builder().message("Точка успешно создана, поздравляю!!!").
                    success(true).build();
        }
    }

    public ResponseForCreateOrUpdate update (PointOfInterest pointOfInterest) {
        PointOfInterestEntity isExists = pointOfInterestRepository.findPointOfInterestEntityByName(pointOfInterest.getName());
        if (isExists != null && !isExists.getId().equals(pointOfInterest.getId())) {
            return ResponseForCreateOrUpdate.builder().message("Точка с таким названием уже существует").
                    success(false).build();
        }else {
            pointOfInterestMapper.entityToDto(
                    pointOfInterestRepository.save(pointOfInterestMapper.dtoToEntity(pointOfInterest)));
            return ResponseForCreateOrUpdate.builder().message("Точка успешно сохранена, поздравляю!!!").
                    success(true).build();
        }
    }

    public Long delete (Long id) {
        List<PointOfInterestRoute> list = pointOfInterestRouteMapper.toList(pointOfInterestRouteRepository.findByPointOfInterestId(id));
        List<Long> routeIds = list.stream().map(PointOfInterestRoute::getRouteId).toList();
        pointOfInterestRepository.deleteById(id);
        for (Long routeId : routeIds) {
            routeService.updatePosition(routeId);
        }
        return id;
    }

    @Transactional
    public List<PointOfInterest> findAll() {
        return pointOfInterestMapper.toList(pointOfInterestRepository.findAll());
    }

    @Transactional
    public PointOfInterest findById(Long id) {
        return pointOfInterestMapper.entityToDto(pointOfInterestRepository.findById(id).orElse(null));
    }



    public Long addLike(Long pointOfInterestId, String clientName) {
        PointOfInterestEntity pointOfInterestEntity = pointOfInterestRepository.findById(pointOfInterestId).orElse(null);
        Long clientId = clientRepository.findClientIdByUsername(clientName);

        ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);
        if (pointOfInterestEntity == null || clientEntity == null) {
            return null;
        }
        else {
            boolean isCreator = clientEntity.getLikedPoints().stream().
                    anyMatch(point -> Objects.equals(point.getId(), pointOfInterestId));
            if (isCreator) {
                deleteLike(pointOfInterestId, clientId);
            }
            else {
                clientEntity.getLikedPoints().add(pointOfInterestEntity);
                pointOfInterestEntity.getLikes().add(clientEntity);
                clientRepository.save(clientEntity);
            }
            List<ClientEntity> list = pointOfInterestEntity.getLikes();
            if(!list.isEmpty()) {
                return (long) list.size();
            }
            else {
                return null;
            }
        }
    }

    public void deleteLike(Long pointOfInterestId, Long clientId) {
        PointOfInterestEntity pointOfInterestEntity = pointOfInterestRepository.findById(pointOfInterestId).orElse(null);
        ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);
        if (pointOfInterestEntity == null || clientEntity == null) {
            return;
        }
        else {
            clientEntity.getLikedPoints().remove(pointOfInterestEntity);
            pointOfInterestEntity.getLikes().remove(clientEntity);
            clientRepository.save(clientEntity);
        }
    }

    public boolean isCreator (String username, long pointOfInterestId) {
        return pointOfInterestRepository.isCreator(username, pointOfInterestId);
    }
}
