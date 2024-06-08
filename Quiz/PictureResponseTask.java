package Quiz;

import java.util.ArrayList;

import static Quiz.TaskTypes.PICTURE_RESPONSE;

public class PictureResponseTask extends QuizTask{

    public PictureResponseTask(QuestionAnsPair QA) {
        list = new ArrayList<>();
        list.add(QA);
        type = PICTURE_RESPONSE;
    }

    public PictureResponseTask(Question q, Answer ans) {
        this(new QuestionAnsPair(q, ans));
        type = PICTURE_RESPONSE;
    }

    public QuestionAnsPair getQA() {
        return list.get(0);
    }
}
