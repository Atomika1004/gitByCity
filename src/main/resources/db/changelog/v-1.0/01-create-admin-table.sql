create table admin (
    credential_id bigint unique,
    id bigint not null,
    fio varchar(255),
    primary key (id)
);

create sequence admin_seq start with 1 increment by 50;

alter table if exists admin
    add constraint FK5wjiqc8ddbtwwn47aj22rfntb
    foreign key (credential_id)
    references credential;