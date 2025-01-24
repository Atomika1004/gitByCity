create table client (
                        credential_id bigint not null unique,
                        id bigint not null,
                        fio varchar(255) unique,
                        primary key (id)
)
GO

create sequence client_seq start with 1 increment by 50
GO

alter table if exists client
    add constraint FK8k42mi4ytlpy76uh276snefd1
    foreign key (credential_id)
    references credential
GO
