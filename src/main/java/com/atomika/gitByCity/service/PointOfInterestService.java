package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.*;
import com.atomika.gitByCity.dto.mapper.PointOfInterestMapper;
import com.atomika.gitByCity.dto.mapper.PointOfInterestRouteMapper;
import com.atomika.gitByCity.entity.*;
import com.atomika.gitByCity.repositories.AttachmentRepository;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    public PointOfInterest create (PointOfInterest pointOfInterest, String clientName) {
        pointOfInterest.setClientId(clientRepository.findClientIdByUsername(clientName));
        return pointOfInterestMapper.entityToDto(
                pointOfInterestRepository.save(pointOfInterestMapper.dtoToEntity(pointOfInterest)));
    }

    public PointOfInterest update (PointOfInterest pointOfInterest) {
        return pointOfInterestMapper.entityToDto(
                pointOfInterestRepository.save(pointOfInterestMapper.dtoToEntity(pointOfInterest)));
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
