package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true, nullable = false)
    String name;

    @Column(length = 1000)
    String description;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false, nullable = false)
    ClientEntity client;

    @Column(name = "client_id")
    Long clientId;

    @ManyToMany(mappedBy = "likedRoutes")
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    List<ClientEntity> likes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "route_id")
    @OrderBy("position ASC")
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    List<PointOfInterestRouteEntity> pointsOfInterest = new ArrayList<>();

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    List<CommentEntity> comments = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @OrderBy("id ASC")
    @Fetch(FetchMode.SUBSELECT)
    List<AttachmentEntity> images = new ArrayList<>();

}
