USE test_schema;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<

DROP TABLE IF EXISTS test_table;
-- remove table if it already exists and start from scratch

CREATE TABLE test_table (
        name CHAR(26)
);

INSERT INTO test_table VALUES
    ("test0"),
    ("test1");