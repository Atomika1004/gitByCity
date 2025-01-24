create table credential (
                            enabled boolean not null,
                            client_id bigint unique,
                            id bigint not null,
                            password_id bigint not null unique,
                            email varchar(255) unique,
                            role varchar(255) not null check (role in ('CLIENT','ADMIN')),
                            username varchar(255) not null unique,
                            primary key (id)
)
GO

create sequence credential_seq start with 1 increment by 50
GO

alter table if exists credential
    add constraint FKp50oh4dyijwj5o52x07th0d9s
    foreign key (client_id)
    references client
GO

alter table if exists credential
    add constraint FK19m314isg7p9oltqx6j93bobt
    foreign key (password_id)
    references passwords
GO