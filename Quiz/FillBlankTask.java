package Quiz;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static Quiz.TaskTypes.FILL_BLANK;

public class FillBlankTask extends QuizTask{
    public static final String BLANK = "<blank>";

    public FillBlankTask(Question Question) {
        list = new ArrayList<>();
        list.add(Question);
        type = FILL_BLANK;
    }

    public Question getQuestion() {
        return list.get(0);
    }

    /*
        This returns array of strings, where each element of array is diverged by answer string.
        With this Fillable text can be generated easily.
     */
    public String[] getFillableQuestionText() {
        String text = getQuestion().getQuestionText();
        String ans = getQuestion().getAnswer().getCorrectAnswerAt(0);
        return text.split(ans);
    }
}
