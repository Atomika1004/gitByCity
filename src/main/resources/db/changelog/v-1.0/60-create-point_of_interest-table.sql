create table point_like (
                            client_id bigint not null,
                            point_of_interest_id bigint not null
)
GO

alter table if exists point_like
    add constraint FKqgpke2i6ojcy20eg5eowmc262
    foreign key (point_of_interest_id)
    references point_of_interest
GO

alter table if exists point_like
    add constraint FK4oi9nyge9ui47a74hf2tp40vj
    foreign key (client_id)
    references client
GO

create table point_of_interest (
                                   latitude float(53) not null,
                                   longitude float(53) not null,
                                   client_id bigint not null,
                                   id bigint not null,
                                   description varchar(1000),
                                   name varchar(255) not null unique,
                                   primary key (id)
)
GO

alter table if exists point_of_interest
    add constraint FKrnpxs3u3x4pd635s67nx9ql24
    foreign key (client_id)
    references client
GO