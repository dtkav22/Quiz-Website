package Quiz;

import java.util.ArrayList;

public abstract class QuizTask {
    protected ArrayList<QuestionAnsPair> list;
    protected int type;
    public int getListSize() {
        return list.size();
    }
    public QuestionAnsPair getQaAt(int i) {
        return list.get(i);
    }
    int getType() {
        return type;
    }
    public boolean isCorrectAnswer(String answer) {
        return getQaAt(0).getAnswer().isCorrect(answer);
    }
}
