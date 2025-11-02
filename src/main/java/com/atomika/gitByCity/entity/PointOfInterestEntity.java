package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "point_of_interest")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointOfInterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true, nullable = false)
    String name;


    @Column(length = 1000)
    String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "point_of_interest_id")
    @OrderBy("id ASC")
    @Fetch(FetchMode.SUBSELECT)
    List<AttachmentEntity> images = new ArrayList<>();

    double longitude;
    double latitude;

    @ManyToMany(mappedBy = "likedPoints")
    List<ClientEntity> likes = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "client_id", insertable = false, updatable = false, nullable = false)
    ClientEntity client;

    @Column(name = "client_id", nullable = false)
    Long clientId;

    @OneToMany(mappedBy = "pointOfInterest", cascade = CascadeType.REMOVE)
    List<PointOfInterestRouteEntity> routes = new ArrayList<>();

    @OneToMany(mappedBy = "pointOfInterest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CommentEntity> comments = new ArrayList<>();
}