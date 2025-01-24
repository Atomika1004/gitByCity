create table comment (
                         client_id bigint,
                         id bigint not null,
                         point_of_interest_id bigint,
                         route_id bigint,
                         text varchar(1000) not null,
                         primary key (id)
)
GO

create sequence comment_seq start with 1 increment by 50
GO

alter table if exists comment
    add constraint FKkh7b1ibqumr31jlf124of15vy
    foreign key (client_id)
    references client
GO

alter table if exists comment
    add constraint FK9swpaupcmjvo8fentonffvmht
    foreign key (point_of_interest_id)
    references point_of_interest
GO

alter table if exists comment
    add constraint FKiryugmnbq3lwpobe0c8yog7o0
    foreign key (route_id)
    references route
GO