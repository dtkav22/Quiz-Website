package Quiz;

import Quiz.Answer;
import Quiz.Question;
import junit.framework.TestCase;

import java.util.ArrayList;

public class TestQuestionAnsPair extends TestCase {
    public void testSample() {
        String qTxt = "is this testSample1?";
        Answer answer = new Answer("yes");
        Question question = new Question(answer, qTxt);

        assertEquals(qTxt, question.getQuestionText());
        assertNull(question.getImgUrl());
        assertEquals("yes", answer.getCorrectAnswerAt(0));
        assertEquals(1, answer.getCorrectAnswersNumber());
        assertEquals(0, answer.getWrongAnswersNumber());
        assertEquals(question, question);
        assertEquals(answer, question.getAnswer());
    }

    public void testAnswerClass() {
        ArrayList<String> cList = new ArrayList<>();
        ArrayList<String> wList = new ArrayList<>();
        wList.add("w1");
        wList.add("w2");
        Answer ans = new Answer("c1", wList);
        assertEquals(2, ans.getWrongAnswersNumber());
        assertEquals("w1", ans.getWrongAnswerAt(0));
        assertEquals("w2", ans.getWrongAnswerAt(1));

        cList.add("c1");
        ans = new Answer(cList);
        assertEquals(1, ans.getCorrectAnswersNumber());

        cList.add("c2");
        ans = new Answer(cList, wList);
        assertEquals("c2", ans.getCorrectAnswerAt(1));
        assertTrue(ans.isCorrect("c1"));
        assertFalse(ans.isCorrect("w1"));

        cList = new ArrayList<>();
        cList.add("Florence Moore//FloMoo");
        cList.add("Washington//George Washington");
        ans = new Answer(cList);
        assertTrue(ans.isCorrect("Florence Moore"));
        assertTrue(ans.isCorrect("FloMoo"));
        assertTrue(ans.isCorrect("Washington"));
        assertTrue(ans.isCorrect("George Washington"));
    }
}
