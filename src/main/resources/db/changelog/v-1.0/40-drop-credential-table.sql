alter table if exists credential
drop constraint if exists FKp50oh4dyijwj5o52x07th0d9s;

alter table if exists credential
    drop constraint if exists FK19m314isg7p9oltqx6j93bobt;

drop table if exists credential cascade;

drop sequence if exists credential_seq;