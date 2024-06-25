package Quiz;

import java.util.ArrayList;

import static Quiz.TaskTypes.MULTIPLE_CHOICE;

public class MultipleChoiceTask extends QuizTask{

    public MultipleChoiceTask(Question question) {
        list = new ArrayList<>();
        list.add(question);
        type = MULTIPLE_CHOICE;
    }

    public Question getQuestion() {
        return getQuestionAt(0);
    }
}
