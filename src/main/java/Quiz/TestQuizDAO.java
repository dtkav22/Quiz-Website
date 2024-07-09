package Quiz;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static Quiz.TaskTypes.QUESTION_RESPONSE;

public class TestQuizDAO extends TestCase {
    private Quiz getCurQuiz() {
        /// crating task 1
        Answer answer = new Answer("Ding Liren");
        Question question = new Question(answer, "Who is current world champion in chess?");
        QuestionResponseTask task = new QuestionResponseTask(question);

        /// creating task 2
        Answer answer2 = new Answer("Spain", new ArrayList<String>(Arrays.asList("Germany", "France", "Brazil")));
        Question question2 = new Question(answer2, "Which national team won world cup in 2010?");
        MultipleChoiceTask task2 = new MultipleChoiceTask(question2);

        ArrayList<QuizTask> list = new ArrayList<>();
        list.add(task);
        list.add(task2);
        return new Quiz("1", list, null, null, false, false, "example");
    }



    public void test1() {
        QuizDAO dao = new QuizDAO();
        Quiz quiz1 = getCurQuiz();
        String id = dao.addQuiz(quiz1);
        Quiz quiz2 = dao.getQuiz(id);

        assertEquals(quiz1.getAuthor_id(), quiz2.getAuthor_id());
        assertEquals(quiz2.getTasksSize(), quiz1.getTasksSize());
        assertEquals(quiz2.getTaskAt(0).getType(), QUESTION_RESPONSE);
        assertEquals(quiz2.getQuizName(), "example");
    }

    public void test2() {
        QuizDAO dao = new QuizDAO();
        Quiz quiz1 = getCurQuiz();
        String id = dao.addQuiz(quiz1);
        Quiz quiz2 = dao.getQuiz(id);
        QuestionResponseTask cur = (QuestionResponseTask) quiz2.getTaskAt(0);

        assertTrue(cur.isCorrectAnswer("Ding Liren"));
        assertFalse(cur.isCorrectAnswer("Magnus Carlsen"));

        MultipleChoiceTask task = (MultipleChoiceTask) quiz2.getTaskAt(1);
        assertFalse(task.isCorrectAnswer("France"));
        assertTrue(task.isCorrectAnswer("Spain"));
        assertFalse(task.isCorrectAnswer("Georgia"));
    }

    public void test3() {
        QuizDAO dao = new QuizDAO();
        Quiz quiz1 = getCurQuiz();
        String id = dao.addQuiz(quiz1);
        Quiz quiz2 = dao.getQuiz(id);
        QuestionResponseTask cur = (QuestionResponseTask) quiz2.getTaskAt(0);
        Question question = cur.getQuestion();

        String ques = "Who is current world champion in chess?";
        assertEquals(question.getQuestionText(), ques);
        assertNull(dao.getQuiz("1000000"));
    }

    public void testMaxScore() {
        QuizDAO dao = new QuizDAO();

        try {
            assertEquals("100.0", dao.getMaxScore("1"));
            assertEquals("80.0", dao.getMaxScore("2"));
            assertNull(dao.getMaxScore("3"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testGetUserQuizzes() {
        QuizDAO dao = new QuizDAO();
        try {
            assertEquals(1, dao.getUserQuizzes("1", 100).size());
            assertEquals(1, dao.getUserQuizzes("2", 100).size());
            assertEquals(0, dao.getUserQuizzes("1", 0).size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testGetPopularQuizzes() {
        QuizDAO dao = new QuizDAO();
        try {
            assertEquals(2, dao.getPopularQuizzes(100).size());
            assertEquals(1, dao.getPopularQuizzes(1).size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testQuizzesByDate() {
        QuizDAO dao = new QuizDAO();
        try {
            ArrayList<String> quizzes = dao.getQuizzesByDate(100, true);
            assertEquals(2, quizzes.size());
            assertEquals("another quiz", dao.getQuiz(quizzes.get(0)).getQuizName());
            assertEquals(0, dao.getQuizzesByDate(0, false).size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testQuizzesByDate2() {
        QuizDAO dao = new QuizDAO();
        try {
            ArrayList<String> quizzes = dao.getQuizzesByDate(100, false);
            assertEquals(2, quizzes.size());
            assertEquals("sample quiz", dao.getQuiz(quizzes.get(0)).getQuizName());
            assertEquals(0, dao.getQuizzesByDate(0, false).size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
