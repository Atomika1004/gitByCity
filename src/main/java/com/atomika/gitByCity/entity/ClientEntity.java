package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String fio;

    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RouteEntity> route = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "route_like",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    List<RouteEntity> likedRoutes = new ArrayList<>();



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "point_like",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "point_of_interest_id"))
    List<PointOfInterestEntity> likedPoints = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id", nullable = false)
    private CredentialEntity credential;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PointOfInterestEntity> pointOfInterest = new ArrayList<>();

}
