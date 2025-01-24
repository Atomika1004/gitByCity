alter table if exists point_of_interest_route
drop constraint if exists FKk015o236s73k5hivb4o3h3x3q
GO

alter table if exists point_of_interest_route
drop constraint if exists FKrl2tg08ya9492kg4os3dm4nx7
GO

drop table if exists point_of_interest_route cascade
GO

drop sequence if exists point_of_interest_route_seq
GO