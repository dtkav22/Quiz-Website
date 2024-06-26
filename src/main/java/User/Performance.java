package User;

public class Performance {
    private final String quiz_id;
    private final double score;
    private final String date;

    public Performance(String quiz_id, double score, String date) {
        this.quiz_id = quiz_id;
        this.score = score;
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getQuiz_id() {
        return quiz_id;
    }
}
