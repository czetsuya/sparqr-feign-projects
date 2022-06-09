create table if not exists platform_user
(
    id         bigserial primary key,
    email      varchar(255) not null,
    alias      varchar(100) not null,
    version    integer,
    created    timestamp    not null,
    created_by varchar(255),
    updated    timestamp,
    updated_by varchar(255),
    disabled   boolean      not null,
    contact_no varchar(255),
    dob        date,
    first_name varchar(50),
    gender     varchar(1),
    last_name  varchar(50)
);

CREATE UNIQUE INDEX idx_alias ON platform_user (alias);
CREATE UNIQUE INDEX idx_email ON platform_user (email);

create table if not exists platform_sso
(
    external_ref      varchar(50) not null primary key,
    identity_provider varchar(50) not null,
    created           timestamp,
    platform_user_id  bigserial   not null
        constraint fk_platform_user
            references platform_user (id),
    constraint uc_platform_user unique (external_ref, identity_provider)
);

create table if not exists platform_user_profile
(
    id               bigserial primary key,
    profession       varchar(255),
    about            varchar(100),
    availability     varchar(50),
    website          varchar(512),
    introduction     text,
    profile_picture  varchar(255),
    version          integer,
    created          timestamp not null,
    created_by       varchar(255),
    updated          timestamp,
    updated_by       varchar(255),
    platform_user_id bigserial not null
        constraint fk_platform_user_profile
            references platform_user (id)
);

create table if not exists platform_user_certification
(
    id                   bigserial primary key,
    version              integer,
    name                 varchar(100) not null,
    issuing_organization varchar(100) not null,
    credential_id        varchar(100) not null,
    issued_date          date         not null,
    expiration_date      date,
    platform_user_id     bigserial    not null
        constraint fk_platform_user_certification
            references platform_user (id)
);

create table if not exists platform_user_education
(
    id                 bigserial primary key,
    version            integer,
    degree             varchar(100) not null,
    school             varchar(255) not null,
    year_of_graduation integer,
    platform_user_id   bigserial    not null
        constraint fk_platform_user_education
            references platform_user (id)
);

create table if not exists platform_user_language
(
    id               bigserial primary key,
    version          integer,
    language_code    varchar(2) not null,
    language_level   varchar(50),
    platform_user_id bigserial  not null
        constraint fk_platform_user_language
            references platform_user (id)
);

create table if not exists platform_user_skill
(
    skill            varchar(100),
    platform_user_id bigserial not null
        constraint fk_platform_user_skill
            references platform_user (id)
);
