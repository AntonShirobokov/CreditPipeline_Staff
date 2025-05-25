drop table t_employee;


create table if not exists t_employee(
    id serial primary key,
    c_username varchar,
    c_password varchar,
    c_role varchar
);


insert into t_employee(c_username, c_password) values ('admin', '{noop}123456');


update t_employee set c_role = 'ROLE_ADMIN' where id = 1;