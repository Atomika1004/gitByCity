alter table if exists attachment
drop constraint if exists FK4qxew3hlbpigge5ucl90nce6x
GO

alter table if exists attachment
drop constraint if exists FKdnr6bbl6ivtiluojis2f0dxfb
GO

drop table if exists attachment cascade
GO

drop sequence if exists attachment_seq
GO
