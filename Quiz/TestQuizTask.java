package Quiz;

import junit.framework.TestCase;

import java.util.ArrayList;

public class TestQuizTask extends TestCase {
    public void testQuestionResponseTask() {
        Question q = new Question("is test?");
        Answer ans = new Answer("yes");
        QuestionResponseTask QR = new QuestionResponseTask(q, ans);
        assertEquals(1, QR.getListSize());
        assertEquals(QR.getQaAt(0), QR.getQA());
    }

    public void testFillBlankTask() {
        Question q = new Question("This is test sample//smpl, where this sample//smpl is important.");
        Answer ans = new Answer("sample//smpl");
        FillBlankTask FB = new FillBlankTask(q, ans);
        assertEquals(1, FB.getListSize());
        assertEquals(FB.getQaAt(0), FB.getQA());

        String[] list = FB.getFillableQuestionText();
        assertEquals("This is test ", list[0]);
        assertEquals(", where this ", list[1]);
        assertEquals(" is important.", list[2]);
    }

    public void testMultipleChoiceTask() {
        Question q = new Question("is 5 more than 4?");
        ArrayList<String> wrongAnswers = new ArrayList<>();
        wrongAnswers.add("no");
        wrongAnswers.add("equals");
        Answer ans = new Answer("yes", wrongAnswers);
        MultipleChoiceTask MC = new MultipleChoiceTask(q, ans);
        assertEquals(1, MC.getListSize());
        assertTrue(MC.isCorrectAnswer("yes"));
        assertEquals(((QuizTask)MC).getQaAt(0), MC.getQA());
    }
}
