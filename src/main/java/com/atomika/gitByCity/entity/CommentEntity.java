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

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false, updatable = false, insertable = false)
    private RouteEntity route;

    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false, updatable = false, insertable = false)
    private ClientEntity client;

    @Column(name = "client_id")
    private Long clientId;

//    @Column(nullable = false)
//    private String path;

    @Column(nullable = false)
    private String text;

//    @CreatedDate
//    private Date date;

//    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<AttachmentEntity> attachment;
}
