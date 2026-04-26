create table orders
(
    id                        bigint generated always as identity not null,
    order_ref                 text                                not null unique,
    username                  text                                not null,
    customer_name             text                                not null,
    customer_email            text                                not null,
    customer_phone            text                                not null,
    delivery_address_line1    text                                ,
    delivery_address_line2    text                                ,
    delivery_address_city     text                                not null,
    delivery_address_state    text                                not null,
    delivery_address_country  text                                not null,
    delivery_address_zip_code text                                not null,
    status                    text                                not null,
    created_at                timestamp                           not null,
    updated_at                timestamp,
    comments                  text,
    primary key (id)
);

create table order_items
(
    id       bigint generated always as identity not null,
    code     text                                not null,
    name     text                                not null,
    price    numeric                             not null,
    quantity integer                             not null,
    order_id bigint                              not null references orders (id)
);