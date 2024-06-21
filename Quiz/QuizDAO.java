package Quiz;

import DataBaseConnectionPool.DataBaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizDAO {
    public int addQuiz(Quiz quiz) {
        /// adding quiz in base
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "INSERT INTO quizzes_table (quiz_author) VALUES(?)";
        try {
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, quiz.author);
            stm.executeUpdate();
            ResultSet res = con.createStatement().executeQuery("SELECT LAST_INSERT_ID();");
            if(res.next()) {
                String id = res.getString(1);
                for(int i = 0; i < quiz.tasks.size(); i++) {
                    addTask(quiz.tasks.get(i), con, id);
                }
                DataBaseConnectionPool.getInstance().closeConnection(con);
                return Integer.parseInt(id);
            } else {
                throw new Exception("Mysql command LAST_INSERT_ID failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addTask(QuizTask task, Connection con, String quizId) {
        String query = "INSERT INTO tasks_table (task_type, quiz_id) VALUES(?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, String.valueOf(task.getType()));
            stm.setString(2, quizId);
            stm.executeUpdate();
            ResultSet res = con.createStatement().executeQuery("SELECT LAST_INSERT_ID();");
            if(res.next()) {
                String id = res.getString(1);
                for(int i = 0; i < task.getListSize(); i++) {
                    addQuestion(task.getQuestionAt(i), con, id);
                }
            } else {
                throw new Exception("Mysql command LAST_INSERT_ID failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addQuestion(Question question, Connection con, String taskId) {
        /// adding question
        String query = "INSERT INTO questions_table (question_text, image, task_id) VALUES(?, ?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, String.valueOf(question.getQuestionText()));
            stm.setString(2, String.valueOf(question.getImgUrl()));
            stm.setString(3, taskId);
            stm.executeUpdate();
            ResultSet res = con.createStatement().executeQuery("SELECT LAST_INSERT_ID();");
            if(res.next()) {
                String id = res.getString(1);
                String ans_query = "INSERT INTO answers_table (answer_text, isCorrect, answer_order, question_id) VALUES(?, ?, ?, ?)";
                for(int i = 0; i < question.getAnswer().getCorrectAnswersNumber(); i++) {
                    PreparedStatement statement = con.prepareStatement(ans_query);
                    statement.setString(1, question.getAnswer().getCorrectAnswerAt(i));
                    statement.setString(2, "1");
                    statement.setString(3, "0");
                    statement.setString(4, id);
                    statement.executeUpdate();
                }
                for(int i = 0; i < question.getAnswer().getWrongAnswersNumber(); i++) {
                    PreparedStatement statement = con.prepareStatement(ans_query);
                    statement.setString(1, question.getAnswer().getWrongAnswerAt(i));
                    statement.setString(2, "0");
                    statement.setString(3, "0");
                    statement.setString(4, id);
                    statement.executeUpdate();
                }
            } else {
                throw new Exception("Mysql command LAST_INSERT_ID failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
