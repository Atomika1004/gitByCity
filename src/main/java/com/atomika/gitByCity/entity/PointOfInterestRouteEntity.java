package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "point_of_interest_route")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointOfInterestRouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "point_of_interest_id", nullable = false, updatable = false, insertable = false)
    private PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    private Long pointOfInterestId;

    @ManyToOne()
    @JoinColumn(name = "route_id", nullable = false, updatable = false, insertable = false)
    private RouteEntity route;

    @Column(name = "route_id", nullable = true)
    private Long routeId;

    private int position;

}

