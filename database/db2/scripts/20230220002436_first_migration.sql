-- // First migration.
-- Migration SQL that makes the change goes here.

CREATE TABLE kanya
(
    id         serial,
    name       text,
    created_at timestamp,
    updated_at timestamp
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE kanya;
