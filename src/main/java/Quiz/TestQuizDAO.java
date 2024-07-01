package Quiz;

import junit.framework.TestCase;

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

}
