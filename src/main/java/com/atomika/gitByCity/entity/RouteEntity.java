package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "route")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private ClientEntity client;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToMany(mappedBy = "likedRoutes")
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<ClientEntity> likes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
            @JoinColumn(name = "route_id")
    @OrderBy("position ASC")
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<PointOfInterestRouteEntity> pointsOfInterest = new ArrayList<>();

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @OrderBy("id ASC")
    @Fetch(FetchMode.SUBSELECT)
    private List<AttachmentEntity> images = new ArrayList<>();

}
