package Quiz;

import DataBaseConnectionPool.DataBaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static Quiz.TaskTypes.*;

public class QuizStorage {
    static QuizStorage instance;
    private ArrayList<Quiz> quizzes;
    public QuizStorage() {
        quizzes = new ArrayList<Quiz>();
    };

    public static QuizStorage getInstance() {
        if(instance == null) {
            instance = new QuizStorage();
            instance.getAllQuizzes();
        }
        return instance;
    }

    public int getNumberOfQuizzes() {
        return quizzes.size();
    }

    public Quiz getQuiz(int index) {
        if(index < 0 || index >= quizzes.size()) {
            System.err.println("Invalid index" );
        }
        return quizzes.get(index);
    }

    synchronized public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        /// adding quiz in base
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "INSERT INTO quizzes_table (quiz_author) VALUES(?)";
        try {
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, quiz.author);
            stm.executeUpdate();
            ResultSet res = con.createStatement().executeQuery("SELECT MAX(quiz_id) FROM quizzes_table");
            if(res.next()) {
                String id = res.getString(1);
                for(int i = 0; i < quiz.tasks.size(); i++) {
                    addTask(quiz.tasks.get(i), con, id);
                }
            } else {
                System.err.println("Quiz hasn't been created");
            }
        } catch (SQLException e) {
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
            ResultSet res = con.createStatement().executeQuery("SELECT MAX(task_id) FROM tasks_table");
            if(res.next()) {
                String id = res.getString(1);
                for(int i = 0; i < task.getListSize(); i++) {
                    addQA(task.getQaAt(i), con, id);
                }
            } else {
                System.err.println("Quiz hasn't been created");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addQA(QuestionAnsPair QA, Connection con, String taskId) {
        /// adding question
        String query = "INSERT INTO questions_table (question_text, image, task_id) VALUES(?, ?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, String.valueOf(QA.getQuestion().getQuestionText()));
            stm.setString(2, String.valueOf(QA.getQuestion().getImgUrl()));
            stm.setString(3, taskId);
            stm.executeUpdate();
            ResultSet res = con.createStatement().executeQuery("SELECT MAX(task_id) FROM tasks_table");
            if(res.next()) {
                String id = res.getString(1);
                String ans_query = "INSERT INTO answers_table (answer_text, isCorrect, answer_order, question_id) VALUES(?, ?, ?, ?)";
                for(int i = 0; i < QA.getAnswer().getCorrectAnswersNumber(); i++) {
                    PreparedStatement statement = con.prepareStatement(ans_query);
                    statement.setString(1, QA.getAnswer().getCorrectAnswerAt(i));
                    statement.setString(2, "1");
                    statement.setString(3, "0");
                    statement.setString(4, id);
                    statement.executeUpdate();
                }
                for(int i = 0; i < QA.getAnswer().getWrongAnswersNumber(); i++) {
                    PreparedStatement statement = con.prepareStatement(ans_query);
                    statement.setString(1, QA.getAnswer().getWrongAnswerAt(i));
                    statement.setString(2, "0");
                    statement.setString(3, "0");
                    statement.setString(4, id);
                    statement.executeUpdate();
                }
            } else {
                System.err.println("Quiz hasn't been created");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void getAllQuizzes() {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        try {
            ResultSet res = con.createStatement().executeQuery("select * from quizzes_table;");
            while(res.next()) {
                String author = res.getString("quiz_author");
                quizzes.add(new Quiz(author, getTask(con, res.getString(1))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<QuizTask> getTask(Connection con, String quiz_id) {
        String query = "select * from tasks_table where quiz_id = '" + quiz_id + "';";
        try {
            ResultSet res = con.createStatement().executeQuery(query);
            ArrayList<QuizTask> list = new ArrayList<QuizTask>();
            while(res.next()) {
                String id = res.getString(1);
                int type = Integer.parseInt(res.getString(2));
                QuizTask task = getQuizTask(getQuestion(con, id), type);
                list.add(task);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<QuestionAnsPair> getQuestion(Connection con, String task_id) {
        String query = "select * from questions_table where task_id = '" + task_id + "';";
        try {
            ResultSet res = con.createStatement().executeQuery(query);
            ArrayList<QuestionAnsPair> list = new ArrayList<QuestionAnsPair>();
            while(res.next()) {
                Question question = new Question(res.getString(3), res.getString(4));
                Answer answer = getAnswer(con, res.getString(1));
                list.add(new QuestionAnsPair(question, answer));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Answer getAnswer(Connection con, String question_id) {
        String query = "select * from answers_table where question_id = '" + question_id + "';";
        try {
            ResultSet res = con.createStatement().executeQuery(query);
            ArrayList<String> correct = new ArrayList<String>();
            ArrayList<String> wrong = new ArrayList<String>();
            while(res.next()) {
                String str = res.getString(3);
                if(res.getString(4).equals("1")) correct.add(str);
                else wrong.add(str);
            }
            return new Answer(correct, wrong);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private QuizTask getQuizTask(ArrayList<QuestionAnsPair> list, int type) {
        if(type == QUESTION_RESPONSE) {
            return new QuestionResponseTask(list.get(0));
        }
        if(type == FILL_BLANK) {
            return new FillBlankTask(list.get(0));
        }
        if(type == MULTIPLE_CHOICE) {
            return new MultipleChoiceTask(list.get(0));
        }
        if(type == PICTURE_RESPONSE) {
            return new PictureResponseTask(list.get(0));
        }
        return null;
    }

    public static void main(String[] args) {
        Question question = new Question("What is the only continent with land in all four hemispheres?");
        Answer answer = new Answer("Africa", new ArrayList<>(Arrays.asList("Asia", "Europe", "South America")));
        QuestionAnsPair QA = new QuestionAnsPair(question, answer);
        QuizTask task = new MultipleChoiceTask(QA);
        Quiz quiz = new Quiz("OOP TEAM 2", task);
        QuizStorage.getInstance().addQuiz(quiz);
    }

}
