package Quiz;

public class QuestionAnsPair {
    private final Question question;
    private final Answer answer;

    public QuestionAnsPair(Question question, Answer answer) {
        this.answer = answer;
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()) return false;
        QuestionAnsPair QA = (QuestionAnsPair) obj;
        return QA.getAnswer().equals(answer) && QA.getQuestion().equals(question);
    }
}
