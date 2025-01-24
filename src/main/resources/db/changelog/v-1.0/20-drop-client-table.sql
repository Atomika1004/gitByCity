alter table if exists client
drop constraint if exists FK8k42mi4ytlpy76uh276snefd1
GO

drop table if exists client cascade
GO

drop sequence if exists client_seq
GO