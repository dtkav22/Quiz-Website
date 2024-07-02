USE test_schema;

DROP TABLE IF EXISTS performances_table;
DROP TABLE IF EXISTS answers_table;
DROP TABLE IF EXISTS questions_table;
DROP TABLE IF EXISTS tasks_table;
DROP TABLE IF EXISTS quizzes_table;
DROP TABLE IF EXISTS users_table;

CREATE TABLE users_table(
                            user_id INT AUTO_INCREMENT,
                            username VARCHAR(30) NOT NULL UNIQUE ,
                            email VARCHAR(30) NOT NULL,
                            password VARCHAR(64) NOT NULL,
                            PRIMARY KEY (user_id)
);
INSERT INTO users_table(username, email, password)
VALUES ('duta', 'tkavadzedimitri@gmail.com', '798fa27c4d4fb5b1158a1d5f2339edc0a21b14b6');



CREATE TABLE quizzes_table (
                               quiz_id INT AUTO_INCREMENT,
                               author_id INT NOT NULL,
                               quiz_name VARCHAR(30) NOT NULL,
                               author_description TEXT DEFAULT (NULL),
                               creation_date DATE DEFAULT(CURRENT_DATE),
                               randomize_tasks BIT NOT NULL,
                               multiple_page BIT NOT NULL,
                               PRIMARY KEY (quiz_id),
                               FOREIGN KEY (author_id) REFERENCES users_table(user_id)
);
INSERT into quizzes_table (author_id, multiple_page, randomize_tasks, quiz_name)
VALUES ('1', 0, 0, "sample quiz");



CREATE TABLE tasks_table (
                             task_id INT AUTO_INCREMENT,
                             task_type INT,
                             quiz_id INT,
                             PRIMARY KEY (task_id),
                             FOREIGN KEY (quiz_id) REFERENCES quizzes_table(quiz_id)
);
INSERT into tasks_table (task_type, quiz_id)
VALUES (0, 1),
       (1, 1),
       (2, 1),
       (0, 1);



CREATE TABLE questions_table (
                                 question_id INT AUTO_INCREMENT,
                                 task_id INT,
                                 question_text text DEFAULT(NULL),
                                 image TEXT DEFAULT(NULL),
                                 PRIMARY KEY (question_id),
                                 FOREIGN KEY (task_id) REFERENCES tasks_table(task_id)
);
INSERT INTO questions_table(task_id, question_text, image)
VALUES(1, 'Who was the first president of Geeorgia?', NULL),
      (4, 'What year was john f kennedy assassinated?', NULL),
      (2, '______ is a fictional detective created by Arthur Conan Doyle.', NULL),
      (3, 'In which year did America gain independence from Britain?', NULL);



CREATE TABLE answers_table (
                               answer_id INT AUTO_INCREMENT,
                               question_id INT,
                               answer_text TEXT DEFAULT(NULL),
                               isCorrect TINYINT(1),
                               answer_order INT DEFAULT(0),
                               PRIMARY KEY (answer_id),
                               FOREIGN KEY (question_id) REFERENCES questions_table(question_id)
);
INSERT INTO answers_table(question_id, answer_text, isCorrect, answer_order)
VALUES(1, 'Zviad Gamsaxurdia', 1, 0),
      (2, '1963', 1, 0),
      (3, 'Sherlock Holmes // Sherlock // Holmes', 1, 0),
      (4, '1774', 0, 0),
      (4, '1776', 1, 0),
      (4, '1789', 0, 0),
      (4, '1821', 0, 0);

CREATE TABLE performances_table(
                                   user_id INT NOT NULL ,
                                   quiz_id INT NOT NULL ,
                                   score DOUBLE NOT NULL,
                                   date DATETIME NOT NULL,
                                   FOREIGN KEY (user_id) REFERENCES users_table(user_id),
                                   FOREIGN KEY (quiz_id) REFERENCES quizzes_table(quiz_id)
);
INSERT INTO performances_table (user_id, quiz_id, score, date)
VALUES (1, 1, 100, '2024-06-26 17:20');

select * from quizzes_table;
select * from users_table;




