create table sites
(
    id serial primary key,
    domain varchar unique not null,
    login varchar not null,
    password varchar not null
);