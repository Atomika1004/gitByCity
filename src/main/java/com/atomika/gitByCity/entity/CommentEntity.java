package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = true, updatable = false, insertable = false)
    RouteEntity route;

    @Column(name = "route_id")
    Long routeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, updatable = false, insertable = false)
    ClientEntity client;

    @Column(name = "client_id")
    Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_of_interest_id", nullable = true, updatable = false, insertable = false)
    PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    Long pointOfInterestId;

    @Column(nullable = false)
    String text;
}