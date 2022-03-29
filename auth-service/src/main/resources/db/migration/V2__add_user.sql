insert into users (username, password, email)
values ('max', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'max_medvedev@gmail.com');

insert into USERS_ROLES(user_id, role_id) values(select ID from USERS where username = 'max', 1);