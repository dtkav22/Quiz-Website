USE test_schema;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<

-- droppint tables
DROP TABLE IF EXISTS users_table;

-- creating tables
CREATE TABLE users_table(
    user_id INT AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL UNIQUE ,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY (user_id)
);


