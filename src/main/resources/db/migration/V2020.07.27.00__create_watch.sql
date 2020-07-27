create table watch (

    id              bigserial       primary key,
    title           text            not null,
    price           integer         not null,
    description     text            ,
    fountain        text            not null,
    mime_type       text            not null
);

create unique index watch_title_unique_constraint
    on watch (title);