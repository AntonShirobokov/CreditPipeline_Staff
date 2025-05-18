drop table t_employee;


create table if not exists t_employee(
    id serial primary key,
    c_username varchar,
    c_password varchar
);