create table users (
    id bigint primary key,
    username varchar(50) unique not null,
    password varchar(50) not null,
    first_name varchar(90) not null,
    last_name varchar(90) not null,
    birth_date date not null
);

create sequence seq_user_id start 1 increment 1;
