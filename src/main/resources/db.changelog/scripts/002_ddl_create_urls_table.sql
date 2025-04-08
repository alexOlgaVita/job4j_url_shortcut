create table urls
(
    id serial primary key,
    url varchar unique not null,
    code varchar not null,
    calls_count integer default 0,
    site_id int  references sites(id)
);