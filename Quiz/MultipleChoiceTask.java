package Quiz;

import java.util.ArrayList;

public class MultipleChoiceTask extends QuizTask{

    public MultipleChoiceTask(QuestionAnsPair QA) {
        list = new ArrayList<>();
        list.add(QA);
    }

    public MultipleChoiceTask(Question q, Answer ans) {
        this(new QuestionAnsPair(q, ans));
    }

    public QuestionAnsPair getQA() {
        return getQaAt(0);
    }
}
