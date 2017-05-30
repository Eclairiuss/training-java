drop schema if exists `computer-database-db`;
  create schema if not exists `computer-database-db`;
  use `computer-database-db`;

  drop table if exists computer;
  drop table if exists company;
  drop table if exists role;
  drop table if exists user;
  drop table if exists user_role;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  create table role (
    id                        tinyint not null auto_increment,
    name                      varchar(20) not null,
    constraint pk_role primary key (id))
  ;

  create table user (
    id                        bigint not null auto_increment,
    username                  varchar(20) not null,
    password                  varchar(32) not null,
    constraint pk_user primary key (id))
  ;

  create table user_role (
    id                        bigint not null auto_increment,
    role_id                   tinyint not null,
    user_id                   bigint not null,
    constraint pk_user_role primary key (id))
  ;


  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  alter table user_role add constraint fk_user_role_role_1 foreign key (role_id) references role (id) on delete restrict on update restrict;
  alter table user_role add constraint fk_user_role_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);
  create index ix_name on company (name);
  create index ix_role_name on role (name);
  create index ix_user_username on user (username);
