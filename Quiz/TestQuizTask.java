package Quiz;

import junit.framework.TestCase;

import java.util.ArrayList;

public class TestQuizTask extends TestCase {
    public void testQuestionResponseTask() {
        Question q = new Question("is test?");
        Answer ans = new Answer("yes");
        QuestionAnsPair QA = new QuestionAnsPair(q, ans);
        QuestionResponseTask QR = new QuestionResponseTask(q, ans);
        assertEquals(1, QR.getListSize());
        assertEquals(QR.getQaAt(0), QR.getQA());
    }

    public void testFillBlankTask() {
        Question q = new Question("This is test sample//smpl, where this sample//smpl is important.");
        Answer ans = new Answer("sample//smpl");
        FillBlankTask QR = new FillBlankTask(q, ans);
        assertEquals(1, QR.getListSize());
        assertEquals(QR.getQaAt(0), QR.getQA());

        String[] list = QR.getFillableQuestionText();
        assertEquals("This is test ", list[0]);
        assertEquals(", where this ", list[1]);
        assertEquals(" is important.", list[2]);
    }
}
