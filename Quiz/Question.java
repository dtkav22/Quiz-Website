package Quiz;

public class Question {
    private final String question;
    private final String imgUrl;
    private final Answer answer;

    public Question(Answer answer, String question, String imgUrl) {
        this.answer = answer;
        this.question = question;
        this.imgUrl = imgUrl;
    }

    public Question(Answer answer, String question) {
        this(answer, question, null);
    }

    public String getQuestionText() {return question;}

    public String getImgUrl() {return imgUrl;}

    public Answer getAnswer() {return answer;}

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()) return false;
        Question q = (Question) obj;
        return q.getQuestionText().equals(question);
    }
}
