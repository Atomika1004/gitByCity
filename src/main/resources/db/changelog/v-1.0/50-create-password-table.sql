create table passwords (
                           id bigint not null,
                           password varchar(255),
                           primary key (id)
)
GO

create sequence passwords_seq start with 1 increment by 50
GO