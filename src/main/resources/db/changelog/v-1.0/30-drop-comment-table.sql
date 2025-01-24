alter table if exists comment
drop constraint if exists FKkh7b1ibqumr31jlf124of15vy
GO

alter table if exists comment
drop constraint if exists FK9swpaupcmjvo8fentonffvmht
GO

alter table if exists comment
drop constraint if exists FKiryugmnbq3lwpobe0c8yog7o0
GO

drop table if exists comment cascade
GO

drop sequence if exists comment_seq
GO