package Quiz;

import java.util.ArrayList;

public class Answer {
    // elements in correctAnswers ArrayList may be formatted as: "George Washington//Washington",
    // which means that those answers are the same.
    private final ArrayList<String> correctAnswers;
    private final ArrayList<String> wrongAnswers;

    public Answer(ArrayList<String> correctAnswers, ArrayList<String> wrongAnswers) {
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        correctAnswers.replaceAll(String::toLowerCase);
    }

    public Answer(ArrayList<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
        wrongAnswers = null;
        correctAnswers.replaceAll(String::toLowerCase);
    }

    public Answer(String correctAnswer, ArrayList<String> wrongAnswers) {
        correctAnswers = new ArrayList<>();
        correctAnswers.add(correctAnswer);
        this.wrongAnswers = wrongAnswers;
        correctAnswers.replaceAll(String::toLowerCase);
    }
    public Answer(String answer) {
        correctAnswers = new ArrayList<>();
        correctAnswers.add(answer);
        wrongAnswers = null;
        correctAnswers.replaceAll(String::toLowerCase);
    }

    public int getCorrectAnswersNumber() {
        return correctAnswers.size();
    }

    public int getWrongAnswersNumber() {
        if(wrongAnswers == null) return 0;
        return wrongAnswers.size();
    }
    public String getCorrectAnswerAt(int i) {
        return correctAnswers.get(i);
    }

    public String getWrongAnswerAt(int i) {
        return wrongAnswers.get(i);
    }

    public boolean isCorrect(String answer) {
        answer = answer.toLowerCase();
        for(String elem : correctAnswers) {
            String[] sameAnswers = elem.split("//");
            for (String sameAnswer : sameAnswers) {
                if (sameAnswer.equals(answer)) return true;
            }
        }
        return false;
    }
}
