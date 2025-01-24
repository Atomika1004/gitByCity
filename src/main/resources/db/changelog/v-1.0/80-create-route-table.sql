create table route (
                       client_id bigint,
                       id bigint not null,
                       description varchar(1000),
                       name varchar(255) not null unique,
                       primary key (id)
)
GO

create sequence route_seq start with 1 increment by 50
GO

create table route_like (
                            client_id bigint not null,
                            route_id bigint not null
)
GO

alter table if exists route
    add constraint FKtny95yjfstmrqb6ioumv1llxf
    foreign key (client_id)
    references client
GO

alter table if exists route_like
    add constraint FKmn4268fcn5jhy4n7r01sxslt5
    foreign key (route_id)
    references route
GO

alter table if exists route_like
    add constraint FKhs9pnhbcfxd866c3iwy46cml
    foreign key (client_id)
    references client
GO