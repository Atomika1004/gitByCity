create table attachment (
                            id bigint not null,
                            point_of_interest_id bigint,
                            route_id bigint,
                            image_url TEXT,
                            primary key (id)
)
GO

create sequence attachment_seq start with 1 increment by 50
GO

alter table if exists attachment
    add constraint FK4qxew3hlbpigge5ucl90nce6x
    foreign key (point_of_interest_id)
    references point_of_interest
GO