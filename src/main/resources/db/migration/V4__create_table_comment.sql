create table comment
(
	id bigint not null
		constraint comment_pk
			primary key,
	parent_id bigint,
	type int,
	gmt_create bigint,
	gmt_modified bigint,
	commentor int,
	like_count bigint,
	content varchar(1024)
);

