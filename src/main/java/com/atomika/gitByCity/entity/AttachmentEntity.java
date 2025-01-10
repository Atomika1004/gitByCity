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
    @JoinColumn(name = "point_of_interest_id", nullable = false, updatable = false, insertable = false)
    private PointOfInterestEntity pointOfInterest;

    @Column(name = "point_of_interest_id")
    private Long pointOfInterestId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl;


}
