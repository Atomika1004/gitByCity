package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.mapper.ClientMapper;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

//    public Client create (Client client) {
//        return clientMapper.entityToDto(
//                clientRepository.save(clientMapper.dtoToEntity(client)));
//    }
//
//    public Client update (Client client) {
//        return clientMapper.entityToDto(
//                clientRepository.save(clientMapper.dtoToEntity(client)));
//    }
//
//    public Long delete (Long id) {
//        clientRepository.deleteById(id);
//        return id;
//    }
//
//    public List<Client> findAll() {
//        return clientMapper.toList(clientRepository.findAll());
//    }
//
//    public Client findById(Long id) {
//        return clientMapper.entityToDto(clientRepository.findById(id).orElse(null));
//    }
}
