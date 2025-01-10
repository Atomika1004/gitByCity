package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "point_of_interest")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointOfInterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
            @JoinColumn(name = "point_of_interest_id")
    List<AttachmentEntity> images = new ArrayList<>();

    private double longitude;
    private double latitude;

    @ManyToMany(mappedBy = "likedPoints", cascade = CascadeType.ALL)
    List<ClientEntity> likes = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "client_id", insertable = false, updatable = false, nullable = false)
    private ClientEntity client;

    @Column(name = "client_id" , nullable = false)
    private Long clientId;

    @OneToMany(mappedBy = "pointOfInterest",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PointOfInterestRouteEntity> routes = new ArrayList<>();
}

