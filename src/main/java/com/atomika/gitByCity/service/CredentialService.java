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
}
