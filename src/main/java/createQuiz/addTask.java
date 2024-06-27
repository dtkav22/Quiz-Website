package createQuiz;

import Quiz.FillBlankTask;
import Quiz.Question;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class addTask extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher view = request.getRequestDispatcher("createQuiz/" + request.getParameter("TaskType") + ".html");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskType = request.getParameter("TaskType");
        if(taskType.equals("FillBlankTask")) {

        }
        if(taskType.equals("MultipleChoiceTask")) {

        }
        if(taskType.equals("PictureResponseTask")) {

        }
        if(taskType.equals("QuestionResponseTask")) {

        }
        response.sendRedirect("/createQuiz");
    }
}
