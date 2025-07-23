DROP TABLE IF EXISTS project;
CREATE TABLE project
(
    id   BIGINT  PRIMARY KEY,
    name VARCHAR(200)
);
DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(
    id   BIGINT  PRIMARY KEY,
    name VARCHAR(60)
);
DROP TABLE IF EXISTS time_record;
CREATE TABLE time_record
(
    id          INT8 PRIMARY KEY,
    employee_id BIGINT        NOT NULL,
    project_id  BIGINT        NOT NULL,
    time_from   TIMESTAMP NOT NULL,
    time_to     TIMESTAMP NOT NULL
);