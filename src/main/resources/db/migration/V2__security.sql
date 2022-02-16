create table users(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null
);

create table roles(
    id         bigserial primary key,
    name       varchar(50) not null
);

create table users_roles(
    user_id    bigint not null references users (id),
    role_id    bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles(name) values('ROLE_USER');
insert into roles(name) values('ROLE_ADMIN');

insert into users(username, password) values('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');
insert into users(username, password) values('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');

insert into users_roles(user_id, role_id) values(1, 1);
insert into users_roles(user_id, role_id) values(2, 2);