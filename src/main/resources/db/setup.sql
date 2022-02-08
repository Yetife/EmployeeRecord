create database if not exists employeeRecord_db;

create user if not exists 'employee_record'@'localhost' identified by 'Employee2@Record';
grant all privileges on employeeRecord_db.* to 'employee_record'@'localhost';
flush privileges;