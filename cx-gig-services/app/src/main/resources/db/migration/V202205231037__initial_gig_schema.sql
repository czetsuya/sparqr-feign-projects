create table if not exists gig
(
    id          bigserial primary key,
    version     integer,
    user_id     bigserial not null,
    status      varchar(25) not null,
    max_booking integer,
    title       varchar(100) not null,
    detail      text
);

CREATE INDEX idx_gig_user_id ON gig (user_id);

create table if not exists gig_featured
(
    id         bigserial primary key,
    version    integer,
    start_date timestamp,
    end_date   timestamp,
    gig_id     bigserial not null
        constraint fk_gig_featured
            references gig (id)
);

create table if not exists gig_gallery_item
(
    id          bigserial primary key,
    version     integer,
    image_url   varchar(100),
    description varchar(100),
    sort_order  integer,
    gig_id      bigserial not null
        constraint fk_gig_gallery_item
            references gig (id)
);

create table if not exists gig_package
(
    id              bigserial primary key,
    version         integer,
    package_type    varchar(50),
    description     varchar(512),
    cost            numeric(23, 12),
    no_of_revisions integer,
    no_of_days      integer,
    deliverables    text,
    gig_id          bigserial not null
        constraint fk_gig_package
            references gig (id)
);