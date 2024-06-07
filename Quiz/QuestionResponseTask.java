package Quiz;

import java.util.ArrayList;

public class QuestionResponseTask extends QuizTask{
    public QuestionResponseTask(QuestionAnsPair QA) {
        list = new ArrayList<>();
        list.add(QA);
    }

    public QuestionResponseTask(Question q, Answer ans) {
        this(new QuestionAnsPair(q, ans));
    }

    public QuestionAnsPair getQA() {
        return list.get(0);
    }
}
