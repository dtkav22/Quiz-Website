package Servlets.Quiz.QuizPage;

import Quiz.QuizTask;
import User.Performance;
import User.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class SubmitQuiz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("values") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not allowed to access this page.");
        } else {
            request.getRequestDispatcher("/jspFiles/SubmitQuiz.jsp").forward(request, response);
        }
    }
    private String convert(long timeUsedInMs) {
        timeUsedInMs /= 1000;
        long seconds = timeUsedInMs % 60;
        timeUsedInMs /= 60;
        long minutes = timeUsedInMs % 60;
        long hrs = timeUsedInMs / 60;
        hrs %= 25;
        StringBuilder result = new StringBuilder();
        long[] arr = {hrs, minutes, seconds};
        for(int i = 0; i < 3; i++) {
            if(arr[i] <= 9) result.append("0");
            result.append(arr[i]);
            if(i != 2) result.append(":");
        }
        return result.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<QuizTask> tasks = (ArrayList<QuizTask>) session.getAttribute("tasks");
        ArrayList<String> values = (ArrayList<String>) session.getAttribute("values");
        int correct = 0;
        for(int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).isCorrectAnswer(values.get(i))) {
                correct++;
            }
        }
        double score = (100.0 * correct) / ((double)tasks.size());
        long timeUsedInMs = System.currentTimeMillis() - ((long)session.getAttribute("startTime"));
        String timeUsed = convert(timeUsedInMs);
        String user_id = (String) session.getAttribute("userId");
        String quiz_id = request.getParameter("quiz_id");
        UserDAO userDAO = new UserDAO();
        Performance performance = new Performance(quiz_id, score, null, user_id, timeUsed);
        try {
            userDAO.addPerformance(performance);
            response.sendRedirect("/QuizSummaryPage?quiz_id=" + quiz_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.removeAttribute("tasks");
        session.removeAttribute("values");
        session.removeAttribute("startTime");
        session.removeAttribute("isMultiplePage");
    }
}
