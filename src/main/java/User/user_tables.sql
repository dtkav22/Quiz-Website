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
VALUES ('duta', 'tkavadzedimitri@gmail.com', '798fa27c4d4fb5b1158a1d5f2339edc0a21b14b6'), -- password is 'duta'
       ('Mariami', 'mivar21@freeuni.edu.ge', '40be99aa88358b324d74673789f53703d9711ea6'), -- password is 'Mariami'
       ('Ioane', 'jtoid20@freeuni.edu.ge', '6dc1dde346851b86913483ec5455ca94ede88455'); -- password is 'Ioane'


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

CREATE TABLE relations_table(
    relation_id INT AUTO_INCREMENT,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    isPending INT NOT NULL,
    PRIMARY KEY (relation_id),
    FOREIGN KEY (user1_id) REFERENCES users_table(user1_id),
    FOREIGN KEY (user2_id) REFERENCES users_table(user2_id)
)

INSERT INTO relations_table (user1_id, user2_id, isPending)
VALUES (1, 2, 0),
       (2, 3, 0),
       (1, 3, 1);

CREATE TABLE challenges_table(
    challenge_id INT NOT NULL,
    quiz_id INT NOT NULL,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    PRIMARY KEY (challenge_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes_table(quiz_id),
    FOREIGN KEY (user1_id) REFERENCES users_table(user1_id),
    FOREIGN KEY (user2_id) REFERENCES users_table(user2_id)
)

CREATE TABLE mails_table(
    mail_id INT NOT NULL,
    mail_text TEXT DEFAULT(NULL),
    send_date DATE DEFAULT (CURRENT_DATE),
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    PRIMARY KEY (mail_id),
    FOREIGN KEY (sender_id) REFERENCES users_table(sender_id),
    FOREIGN KEY (receiver_id) REFERENCES users_table(receiver_id)
)