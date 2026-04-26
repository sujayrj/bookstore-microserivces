CREATE TABLE products
(
    id          bigint generated always as identity not null,
    name        text                                not null,
    code        text                                not null unique,
    description text,
    image_url   text,
    price       numeric                             not null,
    primary key (id)
);