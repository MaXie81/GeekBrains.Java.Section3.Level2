create table categories(
    id              bigserial       primary key,
    title           varchar(255)    unique,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table products(
    id              bigserial       primary key,
    title           varchar(255),
    category_id     bigint references categories (id),
    price           int,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into categories (title) values ('Хлебные изделия');
insert into categories (title) values ('Молочные изделия');

insert into products(title, price, category_id) values('Молоко', 50, 2);
insert into products(title, price, category_id) values('Хлеб', 30, 1);
insert into products(title, price, category_id) values('Сыр', 125, 2);
