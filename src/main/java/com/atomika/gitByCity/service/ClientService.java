package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.mapper.ClientMapper;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.handler.exception.NotFoundException;
import com.atomika.gitByCity.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Transactional(readOnly = true)
    public Client getClientForProfile() {
        Optional<ClientEntity> newClient = clientRepository
                .findClientWithRelations(AuthorizationService.getCurrentUsername());
        ClientEntity clientEntity = newClient.orElseThrow(EntityNotFoundException::new);
            return clientMapper.entityToDto(clientEntity);
    }

    @Transactional
    public Long getClientId() {
        Long id = clientRepository.findClientIdByUsername(AuthorizationService.getCurrentUsername());
        if (id == null) {
            throw new NotFoundException("Пользователь не найден");
        } else {
            return id;
        }
    }
}
