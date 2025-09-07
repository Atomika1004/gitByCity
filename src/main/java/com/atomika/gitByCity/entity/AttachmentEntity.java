package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Base64;

@Entity
@Table(name = "attachment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "point_of_interest_id", nullable = true, updatable = false, insertable = false)
    private PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    private Long pointOfInterestId;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = true, updatable = false, insertable = false)
    private RouteEntity route;

    @Column(name = "route_id")
    private Long routeId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl;


}
