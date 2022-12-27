-- // First migration.
-- Migration SQL that makes the change goes here.

CREATE TABLE thing
(
    id    uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name  text,
    created_date timestamp,
    last_modified_date timestamp
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE thing;
