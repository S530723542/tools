create table member
(
	id bigint not null auto_increment,
    name text,
    password text,
    create_time datetime,
    motify_time datetime,
    is_delete tinyint,
    primary key (id)
);
