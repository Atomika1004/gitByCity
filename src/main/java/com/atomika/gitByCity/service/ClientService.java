package com.atomika.gitByCity.service;

import com.atomika.gitByCity.controllers.AuthorizationController;
import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.mapper.ClientMapper;
import com.atomika.gitByCity.dto.mapper.PointOfInterestMapper;
import com.atomika.gitByCity.dto.mapper.RouteMapper;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.CredentialRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.RouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final PointOfInterestRepository pointOfInterestRepository;
    private final PointOfInterestMapper pointOfInterestMapper;
    private final RouteMapper routeMapper;
    private final RouteRepository routeRepository;
    private final ClientRepository clientRepository;
    private final CredentialRepository credentialRepository;

    @Transactional
    public Client getClientForProfile() {
        return Client.builder().
                fio(clientRepository.findFioByUsername(AuthorizationController.getCurrentUsername())).
                email(credentialRepository.findEmailByUsername(AuthorizationController.getCurrentUsername())).
                createdPointOfInterest(pointOfInterestMapper.toList(pointOfInterestRepository.
                        findListPointsCreatedByClient(AuthorizationController.getCurrentUsername()))).
                estimatedPointOfInterest(pointOfInterestMapper.toList(pointOfInterestRepository.
                        findListLikesFromPointByClient(AuthorizationController.getCurrentUsername()))).
                createdRoute(routeMapper.toList(routeRepository.
                        findListRoutesCreatedByClient(AuthorizationController.getCurrentUsername()))).
                estimatedRoute(routeMapper.toList(routeRepository.
                        findListLikesFromRoutesByClient(AuthorizationController.getCurrentUsername()))).
                build();
    }

    @Transactional
    public Long getClientId() {
        return clientRepository.findClientIdByUsername(AuthorizationController.getCurrentUsername());
    }
}
