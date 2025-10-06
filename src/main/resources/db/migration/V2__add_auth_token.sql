create table if not exists auth_token
(
    id bigint primary key,
    access_token text,
    refresh_token text unique,
    success_expired integer,
    refresh_expired integer,
    credential_id bigint
)