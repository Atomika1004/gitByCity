create table if not exists password
(
    id       bigint primary key,
    password varchar(255)
);

create table if not exists credential
(
    id          bigint primary key,
    enabled     boolean      not null,
    client_id   bigint unique,
    password_id bigint       not null unique,
    email       varchar(255) unique,
    role        varchar(255) not null check (role in ('CLIENT', 'ADMIN')),
    username    varchar(255) not null unique,
    constraint fk_credential_password
        foreign key (password_id) references password (id)
);

create table if not exists client
(
    id            bigint primary key,
    credential_id bigint,
    fio           varchar(255) unique,
    constraint fk_client_credential
        foreign key (credential_id) references credential (id)
);

create table if not exists admin
(
    id            bigint primary key,
    credential_id bigint,
    fio           varchar(255),
    constraint fk_admin_credential
        foreign key (credential_id) references credential (id)
);

create table if not exists point_of_interest
(
    id          bigint primary key,
    latitude    float(53)    not null,
    longitude   float(53)    not null,
    client_id   bigint       not null,
    description text,
    name        varchar(255) not null unique,
    constraint fk_point_of_interest_client
        foreign key (client_id) references client (id)
);

create table if not exists attachment
(
    id                   bigint primary key,
    point_of_interest_id bigint,
    route_id             bigint,
    image_url            TEXT,
    constraint fk_attachment_point_of_interest
        foreign key (point_of_interest_id) references point_of_interest (id)
);

create table if not exists route
(
    id          bigint primary key,
    client_id   bigint,
    description text,
    name        varchar(255) not null unique,
    constraint fk_route_client
        foreign key (client_id) references client (id)
);

create table if not exists route_like
(
    client_id bigint not null,
    route_id  bigint not null,
    constraint fk_route_like_client
        foreign key (client_id) references client (id),
    constraint fk_route_like_route
        foreign key (route_id) references route (id)
);


create table if not exists comment
(
    id                   bigint primary key,
    client_id            bigint,
    point_of_interest_id bigint,
    route_id             bigint,
    text                 text not null,
    constraint fk_comment_client
        foreign key (client_id) references client (id),
    constraint fk_comment_point_of_interest
        foreign key (point_of_interest_id) references point_of_interest (id),
    constraint fk_comment_route
        foreign key (route_id) references route (id)
);


create table if not exists point_like
(
    client_id            bigint not null,
    point_of_interest_id bigint not null,
    constraint fk_point_like_client
        foreign key (client_id) references client (id),
    constraint fk_point_like_point_of_interest
        foreign key (point_of_interest_id) references point_of_interest (id)
);


create table if not exists point_of_interest_route
(
    id                   bigint primary key,
    position             integer not null,
    point_of_interest_id bigint,
    route_id             bigint,
    constraint fk_point_of_interest_route_point_of_interest
        foreign key (point_of_interest_id) references point_of_interest (id),
    constraint fk_point_of_interest_route_to_route
        foreign key (route_id) references route (id)
);