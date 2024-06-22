drop database if exists erp_db;
drop user if exists erp;

create user erp with password 'erp_erp_erp';
create database erp_db with template=template0 encoding='UTF8' owner=erp;
\connect erp_db erp;
alter default privileges grant all on tables to erp;
alter default privileges grant all on sequences to erp;

create table users(
    user_id varchar(10) primary key not null,
    username varchar(50) not null,
    email varchar(50) not null,
    password text not null
);

create table roles(
    role_id varchar(10) primary key not null,
    role_name varchar(50) not null
);

create table user_roles(
    user_id varchar(10) not null,
    role_id varchar(10) not null,
    primary key(user_id, role_id),
    foreign key(user_id) references users(user_id),
    foreign key(role_id) references roles(role_id)
);

create sequence users_user_id_seq increment 1 start 1;
create sequence roles_role_id_seq increment 1 start 1;

