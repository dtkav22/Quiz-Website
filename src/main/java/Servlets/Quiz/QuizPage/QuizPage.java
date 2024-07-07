package Servlets.Quiz.QuizPage;

import Quiz.QuizDAO;
import Quiz.Quiz;
import Quiz.QuizTask;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class QuizPage extends HttpServlet {
    private void shuffle(ArrayList<QuizTask> tasks) {
        Random rnd = new Random();
        for(int i = tasks.size(); i > 0; i--) {
            int index = rnd.nextInt(i);
            QuizTask temp = tasks.get(index);
            tasks.set(index, tasks.get(i - 1));
            tasks.set(i - 1, temp);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("tasks") == null) {
            QuizDAO quizDAO = new QuizDAO();
            Quiz quiz = quizDAO.getQuiz(request.getParameter("quiz_id"));
            ArrayList<QuizTask> tasks = new ArrayList<>();
            for(int i = 0; i < quiz.getTasksSize(); i++) {
                tasks.add(quiz.getTaskAt(i));
            }
            if(quiz.isTasksRandomized()) shuffle(tasks);
            ArrayList<String> values = new ArrayList<>();
            session.setAttribute("tasks", tasks);
            session.setAttribute("values", values);
            session.setAttribute("startTime", System.currentTimeMillis());
        }
        request.getRequestDispatcher("/jspFiles/QuizMultiplePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<QuizTask> tasks = (ArrayList<QuizTask>) session.getAttribute("tasks");
        String quiz_id = request.getParameter("quiz_id");
        String index = request.getParameter("index");
        ArrayList<String> values = (ArrayList<String>) session.getAttribute("values");
        int idx = Integer.parseInt(index);
        if(values.size() == idx)  {
            values.add(request.getParameter("answer"));
        } else {
            values.set(idx, request.getParameter("answer"));
        }
        idx++;
        String url = "/quizPage?quiz_id=" + quiz_id + "&index=" + idx;
        if(idx == tasks.size()) url = "/SubmitQuiz?quiz_id=" + quiz_id;
        response.sendRedirect(url);
    }
}
