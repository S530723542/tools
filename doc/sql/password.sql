drop table if exists password;
create table password
(
	id bigint not null auto_increment,
    password text,
    create_time datetime,
    motify_time datetime,
    is_delete tinyint,
    primary key (id)
);
alter table password comment "password table";
