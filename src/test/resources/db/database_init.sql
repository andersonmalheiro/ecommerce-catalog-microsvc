create table product_vendors
(
    id serial
        constraint product_vendors_pk
            primary key,
    name varchar not null,
    description varchar,
    created_at timestamp with time zone default CURRENT_TIMESTAMP,
    updated_at timestamp with time zone default CURRENT_TIMESTAMP
);

create table product_categories
(
    id serial
        constraint product_categories_pk
            primary key,
    name varchar not null,
    description varchar,
    created_at timestamp with time zone default CURRENT_TIMESTAMP,
    updated_at timestamp with time zone default CURRENT_TIMESTAMP
);