create table resume
(
  uuid      char(36) primary key not null,
  full_name text                 not null
);

create table contact
(
  id          serial,
  resume_uuid char(36) not null REFERENCES resume (uuid) ON DELETE CASCADE,
  type        text     not null,
  value       text     not null
);

create unique index contact_uuid_type_index
  on contact (resume_uuid, contact_type);

create table section
(
  id serial,
  section_type text not null,
  section_value text not null,
  resume_uuid char(36) not null
    constraint section_resume_uuid_fk
    references resume
    on delete cascade,
  constraint section_uuid_type_index
  unique (resume_uuid, section_type)
)
;




