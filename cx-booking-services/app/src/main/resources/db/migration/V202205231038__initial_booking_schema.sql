create table if not exists gig_booking
(
    id          bigserial primary key,
    version     integer,
    booker_id   bigserial,
    gig_id      bigserial,
    package_id  bigserial,
    start_date  date,
    end_date    date,
    status      varchar(25)
);