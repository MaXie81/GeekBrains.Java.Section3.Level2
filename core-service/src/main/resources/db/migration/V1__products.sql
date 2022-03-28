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
    price           decimal(15, 2),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into categories (title) values ('Хлебные изделия');
insert into categories (title) values ('Молочные изделия');

insert into products(title, price, category_id) values('Молоко', 50.36, 2);
insert into products(title, price, category_id) values('Хлеб', 30.01, 1);
insert into products(title, price, category_id) values('Сыр', 125.99, 2);

insert into products(title, price, category_id) values('Молоко2', 10.36, 2);
insert into products(title, price, category_id) values('Молоко3', 20.36, 2);
insert into products(title, price, category_id) values('Молоко4', 30.36, 2);
insert into products(title, price, category_id) values('Молоко5', 40.36, 2);
insert into products(title, price, category_id) values('Молоко6', 50.36, 2);
insert into products(title, price, category_id) values('Молоко7', 60.36, 2);
insert into products(title, price, category_id) values('Молоко8', 70.36, 2);
insert into products(title, price, category_id) values('Молоко9', 80.36, 2);
insert into products(title, price, category_id) values('Молоко10', 90.36, 2);
insert into products(title, price, category_id) values('Молоко11', 100.36, 2);
insert into products(title, price, category_id) values('Молоко12', 110.36, 2);
insert into products(title, price, category_id) values('Молоко13', 120.36, 2);
insert into products(title, price, category_id) values('Молоко14', 130.36, 2);
