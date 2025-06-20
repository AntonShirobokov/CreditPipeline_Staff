drop table t_employee;


create table if not exists t_employee(
    id serial primary key,
    c_username varchar,
    c_password varchar,
    c_firstname varchar,
    c_lastname varchar,
    c_middlename varchar,
    c_role varchar
);

CREATE TABLE IF NOT EXISTS t_reject_rule (
    id SERIAL PRIMARY KEY,
    field_name VARCHAR(64) NOT NULL,
    operation VARCHAR(32) NOT NULL,
    value VARCHAR(128) NOT NULL,
    reason VARCHAR(255) NOT NULL
);


insert into t_employee(c_username, c_password) values ('admin', '{noop}123456');


update t_employee set c_role = 'ROLE_ADMIN' where id = 1;

