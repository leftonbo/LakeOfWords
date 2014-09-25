# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table value_global (
  id                        bigint not null,
  state                     varchar(255),
  back_color                integer,
  title_color               integer,
  constraint pk_value_global primary key (id))
;

create sequence value_global_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists value_global;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists value_global_seq;

