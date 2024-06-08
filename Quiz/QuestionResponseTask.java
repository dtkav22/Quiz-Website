package Quiz;

import java.util.ArrayList;

import static Quiz.TaskTypes.QUESTION_RESPONSE;

public class QuestionResponseTask extends QuizTask{
    public QuestionResponseTask(QuestionAnsPair QA) {
        list = new ArrayList<>();
        list.add(QA);
        type = QUESTION_RESPONSE;
    }

    public QuestionResponseTask(Question q, Answer ans) {
        this(new QuestionAnsPair(q, ans));
        type = QUESTION_RESPONSE;
    }

    public QuestionAnsPair getQA() {
        return list.get(0);
    }
}
