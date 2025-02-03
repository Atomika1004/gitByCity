package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = true, updatable = false, insertable = false)
    private RouteEntity route;

    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, updatable = false, insertable = false)
    private ClientEntity client;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_of_interest_id", nullable = true, updatable = false, insertable = false)
    private PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    private Long pointOfInterestId;

    @Column(nullable = false, length = 1000)
    private String text;


}
