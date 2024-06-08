package Quiz;

import DataBaseConnectionPool.DataBaseConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static Quiz.TaskTypes.*;

public class QuizStorage {
    QuizStorage instance;
    public QuizStorage() {};
    private void doAll() {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        try {
            ResultSet res = con.createStatement().executeQuery("select * from quizzes_table;");
            while(res.next()) {
                getTask(con, res.getString(1));
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
                System.out.println(question.getQuestionText());
                System.out.println(answer.getCorrectAnswerAt(0));
                if(answer.getWrongAnswersNumber() != 0) {
                    System.out.println(answer.getWrongAnswerAt(0));
                }
                System.out.println("------------------");
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
        QuizStorage storage = new QuizStorage();
        storage.doAll();
    }

}
