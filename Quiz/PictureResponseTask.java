package Quiz;

import java.util.ArrayList;

import static Quiz.TaskTypes.PICTURE_RESPONSE;

public class PictureResponseTask extends QuizTask{

    public PictureResponseTask(Question question) {
        list = new ArrayList<>();
        list.add(question);
        type = PICTURE_RESPONSE;
    }

    public Question getQuestion() {
        return list.get(0);
    }
}
