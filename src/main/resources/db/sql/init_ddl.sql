create table if not exists roles (
    id uuid primary key,
    role varchar(255),
    version bigint
);

create table if not exists users (
    id uuid primary key,
    email varchar(255),
    login varchar(255),
    password varchar(255),
    role_id uuid constraint user_role_fk references roles (id),
    version bigint
);

create table if not exists user_roles (
    role_id uuid not null constraint user_role_role_fk references roles,
    user_id uuid not null constraint user_role_user_fk references users,
    version bigint,
    constraint user_role_pk primary key (user_id, role_id)
);

create table if not exists media_storages (
    id uuid not null constraint media_storage_pk primary key,
    content_type varchar(255),
    data bytea,
    file_name varchar(255),
    size integer,
    type_entity varchar(30),
    version bigint,
    created_at timestamp,
    updated_at timestamp
);


create table if not exists coaches (
   id uuid primary key,
   coach_name varchar(100),
   surename varchar(100),
   info text,
   version bigint,
   image_id uuid references media_storages (id)
);

create table if not exists camps (
   id uuid primary key,
   camp_name varchar(100),
   info text,
   place varchar(255),
   price numeric,
   date_start date,
   date_end date,
   count_all integer,
   count_free integer,
   camp_type varchar(10),
   version bigint,
   image_id uuid references media_storages (id)
);

create table if not exists camp_users(
    user_id uuid,
    camp_id uuid,
    is_apruve boolean,
    is_brone boolean,
    is_past boolean,
    primary key (camp_id, user_id),
    foreign key (camp_id) references camps(id),
    foreign key (user_id) references users(id)
);

create table if not exists camp_coaches(

    camp_id uuid not null constraint camp_coach_camp_fk references camps,
    coach_id uuid not null constraint camp_coach_coach_fk references coaches,
    version bigint,
    constraint camp_coach_pk primary key (camp_id, coach_id)
);

create table if not exists user_profile(

    user_id uuid constraint user_profile_pk primary key,
    image_id uuid constraint user_profile_image_fk references media_storages,
    ful_name varchar (255),
    telephone varchar(12),
    birthday date,
    version bigint
);

create table if not exists profile_camps(

    profile_id uuid not null
    constraint profile_camp_profile_fk references user_profile,
    camp_id uuid not null
    constraint profile_camp_camp_fk references camps,
    is_past boolean,
    is_booked boolean,
    version bigint,
    constraint profile_camp_pk primary key (profile_id, camp_id)
);

create table if not exists questions(

    id uuid primary key not null,
    question varchar(255),
    answer text,
    version bigint
);






