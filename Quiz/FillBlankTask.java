package Quiz;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class FillBlankTask extends QuizTask{
    public static final String BLANK = "<blank>";

    public FillBlankTask(QuestionAnsPair QA) {
        list = new ArrayList<>();
        list.add(QA);
    }

    public FillBlankTask(Question q, Answer ans) {
        this(new QuestionAnsPair(q, ans));
    }

    public QuestionAnsPair getQA() {
        return list.get(0);
    }

    /*
        This returns array of strings, where each element of array is diverged by answer string.
        With this Fillable text can be generated easily.
     */
    public String[] getFillableQuestionText() {
        String text = getQA().getQuestion().getQuestionText();
        String ans = getQA().getAnswer().getCorrectAnswerAt(0);
        return text.split(ans);
    }
}
