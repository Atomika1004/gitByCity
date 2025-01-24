create table point_of_interest_route (
                                         position integer not null,
                                         id bigint not null,
                                         point_of_interest_id bigint,
                                         route_id bigint,
                                         primary key (id)
)
GO

create sequence point_of_interest_route_seq start with 1 increment by 50
GO

alter table if exists point_of_interest_route
    add constraint FKk015o236s73k5hivb4o3h3x3q
    foreign key (point_of_interest_id)
    references point_of_interest
GO

alter table if exists point_of_interest_route
    add constraint FKrl2tg08ya9492kg4os3dm4nx7
    foreign key (route_id)
    references route
GO