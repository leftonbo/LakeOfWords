# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint not null,
  text                      varchar(255),
  color1                    integer,
  color2                    integer,
  color3                    integer,
  emotion                   varchar(255),
  create_date               timestamp not null,
  update_date               timestamp not null,
  constraint pk_comment primary key (id))
;

create table value_global (
  id                        bigint not null,
  state                     varchar(255),
  back_color                integer,
  title_color               integer,
  constraint pk_value_global primary key (id))
;

create sequence comment_seq;

create sequence value_global_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists value_global;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists value_global_seq;

