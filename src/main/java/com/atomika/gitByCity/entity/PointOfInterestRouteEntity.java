package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "point_of_interest_route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointOfInterestRouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne()
    @JoinColumn(name = "point_of_interest_id", nullable = false, updatable = false, insertable = false)
    PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    Long pointOfInterestId;

    @ManyToOne()
    @JoinColumn(name = "route_id", nullable = false, updatable = false, insertable = false)
    RouteEntity route;

    @Column(name = "route_id", nullable = true)
    Long routeId;

    int position;

}

