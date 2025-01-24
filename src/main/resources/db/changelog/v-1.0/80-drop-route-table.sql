alter table if exists route
drop constraint if exists FKtny95yjfstmrqb6ioumv1llxf
GO

alter table if exists route_like
drop constraint if exists FKmn4268fcn5jhy4n7r01sxslt5
GO

drop table if exists route cascade
GO

drop table if exists route_like cascade
GO

drop sequence if exists route_seq
GO