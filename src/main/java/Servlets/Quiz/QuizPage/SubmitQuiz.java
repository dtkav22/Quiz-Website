package Servlets.Quiz.QuizPage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SubmitQuiz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("hello");
        request.getRequestDispatcher("/jspFiles/SubmitQuiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("tasks");
        session.removeAttribute("values");
    }
}
