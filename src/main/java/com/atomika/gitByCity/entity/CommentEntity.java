package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;


import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "route_id", nullable = true, updatable = false, insertable = false)
    private RouteEntity route;

    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false, updatable = false, insertable = false)
    private ClientEntity client;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToOne()
    @JoinColumn(name = "point_of_interest_id", nullable = true, updatable = false, insertable = false)
    private PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    private Long pointOfInterestId;

    @Column(nullable = false, length = 1000)
    private String text;


}
