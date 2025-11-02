package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Base64;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "point_of_interest_id", nullable = true, updatable = false, insertable = false)
    PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    Long pointOfInterestId;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = true, updatable = false, insertable = false)
    RouteEntity route;

    @Column(name = "route_id")
    Long routeId;

    @Lob
    @Column(columnDefinition = "TEXT")
    String imageUrl;
}