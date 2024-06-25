package Quiz;

import Quiz.*;
import junit.framework.TestCase;

import java.util.ArrayList;

public class TestQuizTask extends TestCase {
    public void testQuestionResponseTask() {
        Answer ans = new Answer("yes");
        Question q = new Question(ans, "is test?");
        QuestionResponseTask QR = new QuestionResponseTask(q);
        assertEquals(1, QR.getListSize());
        assertEquals(QR.getQuestion(), QR.getQuestionAt(0));
    }

    public void testFillBlankTask() {
        Answer ans = new Answer("sample//smpl");
        Question q = new Question(ans, "This is test sample//smpl, where this sample//smpl is important.");

        FillBlankTask FB = new FillBlankTask(q);
        assertEquals(1, FB.getListSize());
        assertEquals(FB.getQuestionAt(0), FB.getQuestion());

        String[] list = FB.getFillableQuestionText();
        assertEquals("This is test ", list[0]);
        assertEquals(", where this ", list[1]);
        assertEquals(" is important.", list[2]);
    }

    public void testMultipleChoiceTask() {
        ArrayList<String> wrongAnswers = new ArrayList<>();
        wrongAnswers.add("no");
        wrongAnswers.add("equals");
        Answer ans = new Answer("yes", wrongAnswers);
        Question q = new Question(ans, "is 5 more than 4?");

        MultipleChoiceTask MC = new MultipleChoiceTask(q);
        assertEquals(1, MC.getListSize());
        assertTrue(MC.isCorrectAnswer("yes"));
        assertEquals(((QuizTask)MC).getQuestionAt(0), MC.getQuestion());
    }

    public void testPictureResponseTask() {
        Answer ans = new Answer("no");
        Question q = new Question(ans,"is this bird?", "imgUrlSimulation");

        PictureResponseTask PR = new PictureResponseTask(q);
        assertEquals(1, PR.getListSize());
        assertEquals(PR.getQuestionAt(0), PR.getQuestion());
        assertTrue(PR.isCorrectAnswer("no"));
    }
}
