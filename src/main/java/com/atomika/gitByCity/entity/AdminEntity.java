package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id")
    private CredentialEntity credential;
}
