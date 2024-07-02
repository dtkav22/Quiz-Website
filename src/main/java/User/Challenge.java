package User;

public class Challenge {
    public int quiz_id;
    public String user1_id;
    public String user2_id;

    public Challenge(int quiz_id, String user1_id, String user2_id){
        this.quiz_id = quiz_id;
        this.user1_id = user1_id;
        this.user2_id = user2_id;
    }

    public int getQuiz_id(){
        return quiz_id;
    }

    public String getUser1_id(){
        return user1_id;
    }

    public String getUser2_id(){
        return user2_id;
    }

}
