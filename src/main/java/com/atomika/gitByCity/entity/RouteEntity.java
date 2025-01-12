package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false, nullable = false)
    ClientEntity client;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToMany(mappedBy = "likedRoutes")
    List<ClientEntity> likes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
            @JoinColumn(name = "route_id")
    @OrderBy("position ASC")
    List<PointOfInterestRouteEntity> pointsOfInterest = new ArrayList<>();

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CommentEntity> comments = new ArrayList<>();

//    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<RouteEntity> variations;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "route_id", nullable = true)
//    private RouteEntity route;
}

//todo уточнить про null и пустой массив при инициализации обектов