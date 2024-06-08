package Quiz;

import java.util.ArrayList;

import static Quiz.TaskTypes.MULTIPLE_CHOICE;

public class MultipleChoiceTask extends QuizTask{

    public MultipleChoiceTask(QuestionAnsPair QA) {
        list = new ArrayList<>();
        list.add(QA);
        type = MULTIPLE_CHOICE;
    }

    public MultipleChoiceTask(Question q, Answer ans) {
        this(new QuestionAnsPair(q, ans));
        type = MULTIPLE_CHOICE;
    }

    public QuestionAnsPair getQA() {
        return getQaAt(0);
    }
}
