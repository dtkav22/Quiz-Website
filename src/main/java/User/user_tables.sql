USE test_schema;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<

-- Read quiz_tables.sql comments to understand how to start database correctly.

-- creating tables
CREATE TABLE users_table(
    user_id INT AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL UNIQUE ,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY (user_id)
);
INSERT INTO users_table(username, email, password)
VALUES ('duta', 'tkavadzedimitri@gmail.com', '798fa27c4d4fb5b1158a1d5f2339edc0a21b14b6'); -- password is 'duta'

CREATE TABLE performances_table(
    user_id INT NOT NULL ,
    quiz_id INT NOT NULL ,
    score DOUBLE NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users_table(user_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes_table(quiz_id)
);
INSERT INTO performances_table (user_id, quiz_id, score, date)
VALUES (1, 1, 100, '2024-06-26 17:20')



