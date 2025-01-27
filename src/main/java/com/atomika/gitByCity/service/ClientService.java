package com.atomika.gitByCity.service;

import com.atomika.gitByCity.controllers.AuthorizationController;
import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.mapper.PointOfInterestMapper;
import com.atomika.gitByCity.dto.mapper.RouteMapper;
import com.atomika.gitByCity.exception.NotFoundException;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.CredentialRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.RouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private static final Logger log = LogManager.getLogger(ClientService.class);
    private final PointOfInterestRepository pointOfInterestRepository;
    private final PointOfInterestMapper pointOfInterestMapper;
    private final RouteMapper routeMapper;
    private final RouteRepository routeRepository;
    private final ClientRepository clientRepository;
    private final CredentialRepository credentialRepository;

    @Transactional
    public Client getClientForProfile() {
        String username = AuthorizationController.getCurrentUsername();
        return Client.builder().
                fio(clientRepository.findFioByUsername(username)).
                email(credentialRepository.findEmailByUsername(username)).
                createdPointOfInterest(pointOfInterestMapper.toList(pointOfInterestRepository.
                        findListPointsCreatedByClient(username))).
                estimatedPointOfInterest(pointOfInterestMapper.toList(pointOfInterestRepository.
                        findListLikesFromPointByClient(username))).
                createdRoute(routeMapper.toList(routeRepository.
                        findListRoutesCreatedByClient(username))).
                estimatedRoute(routeMapper.toList(routeRepository.
                        findListLikesFromRoutesByClient(username))).
                build();
    }


    @Transactional
    public Long getClientId() {
        Long id = clientRepository.findClientIdByUsername(AuthorizationController.getCurrentUsername());
        if (id == null) {
            throw new NotFoundException("Пользователь не найден");
        } else {
            return id;
        }
    }
}
