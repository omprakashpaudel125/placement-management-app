create database if not exists placements;
use placements;
create table if not exists applicant(id integer auto_increment primary key, Name varchar(30),Age integer, Sex varchar(10), Education varchar(20),Extra text, Salary integer,Field integer, Login varchar(20), Pwd varchar(30));
create table if not exists company(id integer auto_increment primary key, Name varchar(20), Vacancy text, Salary integer, Field varchar(20), Login varchar(20), Pwd varchar(20));