package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Admin;
import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.Credential;
import com.atomika.gitByCity.dto.Role;
import com.atomika.gitByCity.dto.auth.SignInRequest;
import com.atomika.gitByCity.entity.CredentialEntity;
import com.atomika.gitByCity.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialService {
    private final CredentialService credentialService;
    private final AdminService adminService;
    private final ClientService clientService;

    private final JwtUserDetailsService userDetailsService;


    public void initial() {
//        Credential credential = credentialService.create(Credential.builder().
//                username("user1")
//                .password("123")
//                .email("atom@mail.ru")
//                .role(Role.CLIENT)
//                .build());
//        Credential credential1 = credentialService.create(Credential.builder().
//                username("user2")
//                .password("1234")
//                .email("atomika@mail.ru")
//                .role(Role.CLIENT)
//                .build());
//
//        Credential credential2 = credentialService.create(Credential.builder().
//                username("user3")
//                .password("12345")
//                .email("atomika12@mail.ru")
//                .role(Role.CLIENT)
//                .build());

//        adminService.create(Admin.builder().
//                id(1L)
//                .fio("Антон Павлович Чехов")
//                .credential(credential).build());



        userDetailsService.createClient(SignInRequest.builder()
                .username("user1")
                .pwd("1234")
                .build());


        userDetailsService.createClient(SignInRequest.builder()
                .username("user2")
                .pwd("12345")
                .build());
//        userDetailsService.createAdmin(SignInRequest.builder()
//                .username("admin1")
//                .pwd("12345")
//                .build());


//        clientService.create(Client.builder().
//                id(1L)
//                .fio("Алексей Вячеславович Попов")
//                .credential(credential1).build());
//        clientService.create(Client.builder().
//                id(1L)
//                .fio("Валерия Романовна Фролович")
//                .credential(credential3).build());
    }
}
