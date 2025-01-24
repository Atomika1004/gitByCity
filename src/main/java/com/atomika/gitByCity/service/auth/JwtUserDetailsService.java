package com.atomika.gitByCity.service.auth;

import com.atomika.gitByCity.dto.AuthUser;
import com.atomika.gitByCity.dto.Role;
import com.atomika.gitByCity.dto.auth.SignInRequest;
import com.atomika.gitByCity.dto.auth.SignInResponse;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.CredentialEntity;
import com.atomika.gitByCity.entity.PasswordEntity;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.CredentialRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CredentialRepository credentialRepository;
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CredentialEntity credential = credentialRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", username))
        );

        return AuthUser.builder()
                .username(credential.getUsername())
                .password(credential.getPassword().getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(credential.getRole().name())))
                .enabled(credential.isEnabled())
                .build();
    }

    public SignInResponse createClient(SignInRequest request) {
        Optional<CredentialEntity> credential = credentialRepository.findByUsername(request.getUsername());
        boolean isNewClient = clientRepository.findClientByFio(request.getFio());
        boolean isNewEmail = credentialRepository.isExistEmailByUsername(request.getEmail());
        if (credential.isPresent()) {
            return SignInResponse.builder().message("Пользователь с таким логином уже существует").build();
        }
        else if (isNewClient) {
            return SignInResponse.builder().message("Пользователь с таким фио уже существует").build();
        }
        else if (isNewEmail) {
            return SignInResponse.builder().message("Пользователь с такой почтой уже существует").build();
        }
        else {
            CredentialEntity client = CredentialEntity.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(new PasswordEntity(request.getPwd()))
                    .enabled(true)
                    .role(Role.CLIENT)
                    .build();

            ClientEntity client1 = ClientEntity.builder().
                    fio(request.getFio()).
                    credential(client).
                    build();

            client.setClient(client1);
            credentialRepository.save(client);
            return SignInResponse.builder().message("Вы успешно зарегистрировались").build();
        }
    }

    public void createAdmin(SignInRequest request) {

        CredentialEntity admin = CredentialEntity.builder()
                .username(request.getUsername())
                .password(new PasswordEntity(request.getPwd()))
                .enabled(true)
                .role(Role.ADMIN)
                .build();

        credentialRepository.save(admin);
    }
}
