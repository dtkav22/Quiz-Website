package Quiz;

import java.util.ArrayList;

import static Quiz.TaskTypes.QUESTION_RESPONSE;

public class QuestionResponseTask extends QuizTask{
    public QuestionResponseTask(Question question) {
        list = new ArrayList<>();
        list.add(question);
        type = QUESTION_RESPONSE;
    }

    public Question getQuestion() {
        return list.get(0);
    }
}
