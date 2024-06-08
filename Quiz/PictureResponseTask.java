package Quiz;

import java.util.ArrayList;

public class PictureResponseTask extends QuizTask{

    public PictureResponseTask(QuestionAnsPair QA) {
        list = new ArrayList<>();
        list.add(QA);
    }

    public PictureResponseTask(Question q, Answer ans) {
        this(new QuestionAnsPair(q, ans));
    }

    public QuestionAnsPair getQA() {
        return list.get(0);
    }
}
