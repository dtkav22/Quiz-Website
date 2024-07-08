package Servlets.Quiz.QuizPage;

import User.Performance;
import User.UserDAO;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PerformancesHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_id = (String) session.getAttribute("userId");
        String quiz_id = request.getParameter("quiz_id");
        String order = request.getParameter("order");
        try {
            ArrayList<Performance> performances = new UserDAO().getUserPerformanceOnQuiz(user_id, quiz_id, 5, order);
            Gson gson = new Gson();
            String JsonString = gson.toJson(performances);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
