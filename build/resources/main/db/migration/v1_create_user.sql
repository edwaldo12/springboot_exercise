create table app_user (
      id              uuid primary key,
      email           varchar(255) not null unique,
      password_hash   varchar(255) not null,
      display_name    varchar(100) not null
);
