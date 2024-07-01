package Quiz;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class Quiz {
    private final ArrayList<QuizTask> tasks;
    private final String author_id; /// other info are needed as well
    private final String authorDescription;
    private final boolean randomizeTasks;
    private final boolean onMultiplePage;
    private final java.sql.Date creationDate;
    private final String quiz_name;

    public Quiz(String author_id, ArrayList<QuizTask> tasks, String authorDescription, java.sql.Date creationDate, boolean randomizeTasks, boolean onMultiplePage, String quizName) {
        this.tasks = tasks;
        this.author_id = author_id;
        this.randomizeTasks = randomizeTasks;
        this.onMultiplePage = onMultiplePage;
        this.authorDescription = authorDescription;
        this.creationDate = creationDate;
        quiz_name = quizName;
    }
    public Quiz(String author_id, QuizTask task, String authorDescription, java.sql.Date creationDate, boolean randomizeTasks, boolean onMultiplePage, String quizName) {
        quiz_name = quizName;
        tasks = new ArrayList<>();
        tasks.add(task);
        this.author_id = author_id;
        this.randomizeTasks = randomizeTasks;
        this.onMultiplePage = onMultiplePage;
        this.authorDescription = authorDescription;
        this.creationDate = creationDate;
    }
    public Quiz(String author_id, String authorDescription, java.sql.Date creationDate, boolean randomizeTasks, boolean onMultiplePage, String quizName) {
        this.author_id = author_id;
        quiz_name = quizName;
        tasks = new ArrayList<>();
        this.randomizeTasks = randomizeTasks;
        this.onMultiplePage = onMultiplePage;
        this.authorDescription = authorDescription;
        this.creationDate = creationDate;
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

    public String getAuthorDescription() {
        return authorDescription;
    }

    public int getTasksSize() {
        return tasks.size();
    }

    public boolean isTasksRandomized() {
        return randomizeTasks;
    }

    public boolean isOnMultiplePage() {
        return onMultiplePage;
    }

    public java.sql.Date getCreationDate() {
        return creationDate;
    }

    public String getQuizName() {
        return quiz_name;
    }
}
