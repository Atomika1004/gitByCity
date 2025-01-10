package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Credential;
import com.atomika.gitByCity.dto.mapper.CredentialMapper;
import com.atomika.gitByCity.repositories.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final CredentialMapper credentialMapper;

//    public Credential create (Credential credential) {
//        return credentialMapper.entityToDto(
//                credentialRepository.save(credentialMapper.dtoToEntity(credential)));
//    }
//
//    public Credential update (Credential credential) {
//        return credentialMapper.entityToDto(
//                credentialRepository.save(credentialMapper.dtoToEntity(credential)));
//    }
//
//    public Long delete (Long id) {
//        credentialRepository.deleteById(id);
//        return id;
//    }
//
//    public List<Credential> findAll() {
//        return credentialMapper.toList(credentialRepository.findAll());
//    }
//
//    public Credential findById(Long id) {
//        return credentialMapper.entityToDto(credentialRepository.findById(id).get());
//    }
}
