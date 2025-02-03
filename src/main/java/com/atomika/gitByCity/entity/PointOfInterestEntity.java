package com.atomika.gitByCity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "point_of_interest")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointOfInterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;


    @Column(length = 1000)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "point_of_interest_id")
    @OrderBy("id ASC")
    @Fetch(FetchMode.SUBSELECT)
    private List<AttachmentEntity> images = new ArrayList<>();

    private double longitude;
    private double latitude;

    @ManyToMany(mappedBy = "likedPoints")
    private List<ClientEntity> likes = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "client_id", insertable = false, updatable = false, nullable = false)
    private ClientEntity client;

    @Column(name = "client_id" , nullable = false)
    private Long clientId;

    @OneToMany(mappedBy = "pointOfInterest",cascade = CascadeType.REMOVE)
    private List<PointOfInterestRouteEntity> routes = new ArrayList<>();

    @OneToMany(mappedBy = "pointOfInterest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();
}

