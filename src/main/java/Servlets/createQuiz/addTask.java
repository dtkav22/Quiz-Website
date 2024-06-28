package Servlets.createQuiz;

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
        RequestDispatcher view = request.getRequestDispatcher("createQuiz/" + request.getParameter("TaskType") + ".html");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskType = request.getParameter("TaskType");
        HttpSession session = request.getSession();
        ArrayList<QuizTask> tasks = (ArrayList<QuizTask>) session.getAttribute("Tasks");
        if(taskType.equals("FillBlankTask")) {
            String questionText = request.getParameter("Question");
            String answerText = request.getParameter("Answer");
            Answer answer = new Answer(answerText);
            Question question = new Question(answer, questionText, null);
            tasks.add(new FillBlankTask(question));
        }
        if(taskType.equals("MultipleChoiceTask")) {
            String questionText = request.getParameter("Question");
            String correctAnswer = request.getParameter("correctAnswer");
            String[] wrongAnswers = request.getParameterValues("wrongAnswers");
            ArrayList<String> wrongAnswersArray = new ArrayList<>();
            Collections.addAll(wrongAnswersArray, wrongAnswers);
            Answer answer = new Answer(correctAnswer, wrongAnswersArray);
            Question question = new Question(answer, questionText, null);
            tasks.add(new MultipleChoiceTask(question));
        }
        if(taskType.equals("PictureResponseTask")) {
            String questionText = request.getParameter("Question");
            String answerText = request.getParameter("Answer");
            String imgUrl = request.getParameter("ImageUrl");
            Answer answer = new Answer(answerText);
            Question question = new Question(answer, questionText, imgUrl);
            tasks.add(new PictureResponseTask(question));
        }
        if(taskType.equals("QuestionResponseTask")) {
            String questionText = request.getParameter("Question");
            String answerText = request.getParameter("Answer");
            Answer answer = new Answer(answerText);
            Question question = new Question(answer, questionText, null);
            tasks.add(new QuestionResponseTask(question));
        }
        response.sendRedirect("/createQuiz");
    }
}
