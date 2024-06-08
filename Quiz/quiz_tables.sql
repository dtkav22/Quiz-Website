USE test_schema;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<

-- droping tables note that oreder do metters


DROP TABLE IF EXISTS answers_table;
DROP TABLE IF EXISTS questions_table;
DROP TABLE IF EXISTS tasks_table;
DROP TABLE IF EXISTS quizzes_table;

-- creating tables

CREATE TABLE quizzes_table (
                               quiz_id INT AUTO_INCREMENT,
                               quiz_author VarCHAR(100),
                               quiz_time DATE DEFAULT(CURRENT_DATE),
                               PRIMARY KEY (quiz_id)
);

CREATE TABLE tasks_table (
                             task_id INT AUTO_INCREMENT,
                             task_type INT,
                             quiz_id INT,
                             PRIMARY KEY (task_id),
                             FOREIGN KEY (quiz_id) REFERENCES quizzes_table(quiz_id)
);

CREATE TABLE questions_table (
                                 question_id INT AUTO_INCREMENT,
                                 task_id INT,
                                 question_text text DEFAULT(NULL),
                                 image VARCHAR(50) DEFAULT(NULL),
                                 PRIMARY KEY (question_id),
                                 FOREIGN KEY (task_id) REFERENCES tasks_table(task_id)
);

CREATE TABLE answers_table (
                               answer_id INT AUTO_INCREMENT,
                               question_id INT,
                               answer_text TEXT DEFAULT(NULL),
                               isCorrect TINYINT(1),
                               answer_order INT DEFAULT(0),
                               PRIMARY KEY (answer_id),
                               FOREIGN KEY (question_id) REFERENCES questions_table(question_id)
);

INSERT into quizzes_table (quiz_author)
VALUES ('OOP team');

INSERT into tasks_table (task_type, quiz_id)
VALUES (0, 1),
       (1, 1),
       (2, 1),
       (0, 1);

INSERT INTO questions_table(task_id, question_text, image)
VALUES(1, 'Who was the first president of Geeorgia?', NULL),
      (4, 'What year was john f kennedy assassinated?', NULL),
      (2, "______ is a fictional detective created by Arthur Conan Doyle.", NULL),
      (3, "In which year did America gain independence from Britain?", NULL);

INSERT INTO answers_table(question_id, answer_text, isCorrect, answer_order)
VALUES(1, "Zviad Gamsaxurdia", 1, 0),
      (2, "1963", 1, 0),
      (3, "Sherlock Holmes // Sherlock // Holmes", 1, 0),
      (4, "1774", 0, 0),
      (4, "1776", 1, 0),
      (4, "1789", 0, 0),
      (4, "1821", 0, 0);

select * from questions_table;
select * from answers_table;
select * from tasks_table;
select * from quizzes_table;
