package User;

import java.sql.Time;

public class Performance {
    private final String quiz_id;
    private final double score;
    private final String date;
    private final String user_id;
    private final String used_time;

    public Performance(String quiz_id, double score, String date, String user_id, String used_time) {
        this.quiz_id = quiz_id;
        this.score = score;
        this.date = date;
        this.user_id = user_id;
        this.used_time = used_time;
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

    public String getUser_id() {
        return user_id;
    }

    public String getUsed_time() {
        return used_time;
    }
}
