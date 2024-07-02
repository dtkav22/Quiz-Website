package Servlets.Quiz.createQuiz;

import Quiz.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class addTask extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher view = request.getRequestDispatcher("htmlFiles/" + request.getParameter("TaskType") + ".html");
        view.forward(request, response);
    }

    private String getJointAnswer(String[] answers) {
        StringBuilder jointAnswerText = new StringBuilder();
        for(String answerText : answers) {
            jointAnswerText.append(answerText);
            jointAnswerText.append("//");
        }
        return jointAnswerText.toString();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskType = request.getParameter("TaskType");
        HttpSession session = request.getSession();
        ArrayList<QuizTask> tasks = (ArrayList<QuizTask>) session.getAttribute("Tasks");
        String questionText = request.getParameter("Question");
        String[] answerTexts = request.getParameterValues("correctAnswers");
        String correctAnswer = getJointAnswer(answerTexts);
        if(taskType.equals("FillBlankTask")) {
            Answer answer = new Answer(correctAnswer);
            Question question = new Question(answer, questionText, null);
            tasks.add(new FillBlankTask(question));
        }
        if(taskType.equals("MultipleChoiceTask")) {
            String[] wrongAnswers = request.getParameterValues("wrongAnswers");
            ArrayList<String> wrongAnswersArray = new ArrayList<>();
            Collections.addAll(wrongAnswersArray, wrongAnswers);
            Answer answer = new Answer(answerTexts[0], wrongAnswersArray);
            Question question = new Question(answer, questionText, null);
            tasks.add(new MultipleChoiceTask(question));
        }
        if(taskType.equals("PictureResponseTask")) {
            String imgUrl = request.getParameter("ImageUrl");
            Answer answer = new Answer(correctAnswer);
            Question question = new Question(answer, questionText, imgUrl);
            tasks.add(new PictureResponseTask(question));
        }
        if(taskType.equals("QuestionResponseTask")) {
            Answer answer = new Answer(correctAnswer);
            Question question = new Question(answer, questionText, null);
            tasks.add(new QuestionResponseTask(question));
        }
        response.sendRedirect("/createQuiz");
    }
}
