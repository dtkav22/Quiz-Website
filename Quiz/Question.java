package Quiz;

public class Question {
    private final String question;
    private final String imgUrl;

    public Question(String question, String imgUrl) {
        this.question = question;
        this.imgUrl = imgUrl;
    }

    public Question(String question) {
        this(question, null);
    }

    public String getQuestionText() {return question;}

    public String getImgUrl() {return imgUrl;}

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()) return false;
        Question q = (Question) obj;
        return q.getQuestionText().equals(question);
    }
}
