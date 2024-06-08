package Quiz;

import java.util.ArrayList;

public abstract class QuizTask {
    ArrayList<QuestionAnsPair> list;
    public int getListSize() {
        return list.size();
    }
    public QuestionAnsPair getQaAt(int i) {
        return list.get(i);
    }

    public boolean isCorrectAnswer(String answer) {
        return getQaAt(0).getAnswer().isCorrect(answer);
    }
}
