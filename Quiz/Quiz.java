package Quiz;

import java.util.ArrayList;

public class Quiz {
    ArrayList<QuizTask> tasks;
    String author; /// other info are needed as well
    public Quiz(String author, ArrayList<QuizTask> tasks) {
        this.tasks = tasks;
        this.author = author;
    }
    public Quiz(String author, QuizTask task) {
        tasks = new ArrayList<QuizTask>();
        tasks.add(task);
        this.author = author;
    }
    public Quiz(String author) {
        this.author = author;
    }
    public void addTask(QuizTask task) {
        tasks.add(task);
    }
}
