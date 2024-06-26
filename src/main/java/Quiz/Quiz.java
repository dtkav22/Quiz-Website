package Quiz;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class Quiz {
    private final ArrayList<QuizTask> tasks;
    private final String author_id; /// other info are needed as well
    private String authorDescription = null;

    public Quiz(String author_id, ArrayList<QuizTask> tasks) {
        this.tasks = tasks;
        this.author_id = author_id;
    }
    public Quiz(String author_id, QuizTask task) {
        tasks = new ArrayList<>();
        tasks.add(task);
        this.author_id = author_id;
    }
    public Quiz(String author_id) {
        this.author_id = author_id;
        tasks = new ArrayList<>();
    }

    public void addTask(QuizTask task) {
        tasks.add(task);
    }

    public QuizTask getTaskAt(int i) {
        return tasks.get(i);
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthorDescription(String text){
        authorDescription = text;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public int getTasksSize() {
        return tasks.size();
    }
}
