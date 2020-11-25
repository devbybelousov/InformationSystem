CREATE TABLE information_system
(
    id    int auto_increment
        primary key,
    title varchar(100) null
);

CREATE TABLE roles
(
    role_id int auto_increment
        primary key,
    name    varchar(20) not null
);

CREATE TABLE unit
(
    unit_id int auto_increment
        primary key,
    title   varchar(100) not null
);

CREATE TABLE department
(
    department_id int auto_increment
        primary key,
    title         varchar(100) not null,
    unit_id       int          not null,
    constraint FK9u44ynbcut0e5t0ov7tuokdbk
        foreign key (unit_id) references unit (unit_id)
);

CREATE TABLE user_role
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id)
)
    engine = MyISAM;

CREATE index FKt7e7djp752sqn6w22i6ocqy6q
    on user_role (role_id);

CREATE TABLE users
(
    user_id       int auto_increment
        primary key,
    user_name     varchar(50)  null,
    password      varchar(100) not null,
    name          varchar(50)  not null,
    last_name     varchar(50)  not null,
    middle_name   varchar(75)  null,
    department_id int          null,
    constraint FKfi832e3qv89fq376fuh8920y4
        foreign key (department_id) references department (department_id)
);

CREATE TABLE request
(
    request_id  int auto_increment
        primary key,
    user_id     int         not null,
    system_id   int         not null,
    filing_date datetime    not null,
    expiry_date datetime    not null,
    status      varchar(10) null,
    constraint FK7vrq809dxla5762q0jw6qxlmx
        foreign key (system_id) references information_system (id),
    constraint FKg03bldv86pfuboqfefx48p6u3
        foreign key (user_id) references users (user_id)
);