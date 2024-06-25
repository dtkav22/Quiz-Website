package Quiz;

import java.util.ArrayList;

public abstract class QuizTask {
    protected ArrayList<Question> list;
    protected int type;
    public int getListSize() {
        return list.size();
    }
    public Question getQuestionAt(int i) {
        return list.get(i);
    }
    public int getType() {
        return type;
    }
    public boolean isCorrectAnswer(String answer) {
        return getQuestionAt(0).getAnswer().isCorrect(answer);
    }
}
