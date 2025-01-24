alter table if exists point_like
drop constraint if exists FKqgpke2i6ojcy20eg5eowmc262;

alter table if exists point_like
drop constraint if exists FK4oi9nyge9ui47a74hf2tp40vj;

drop table if exists point_like cascade;

alter table if exists point_of_interest
    drop constraint if exists FKrnpxs3u3x4pd635s67nx9ql24;

drop table if exists point_of_interest cascade;

drop sequence if exists point_of_interest_seq;