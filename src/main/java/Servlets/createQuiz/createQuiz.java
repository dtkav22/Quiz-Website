package Servlets.createQuiz;

import Quiz.Quiz;
import Quiz.QuizDAO;
import Quiz.QuizTask;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

public class createQuiz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("Tasks") == null) {
            session.setAttribute("Tasks", new ArrayList<QuizTask>());
        }
        request.getRequestDispatcher("createQuiz/createQuiz.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuizDAO quizDAO = new QuizDAO();
        HttpSession session = request.getSession();
        String author_id = (String) session.getAttribute("userId");
        ArrayList<QuizTask> tasks = (ArrayList<QuizTask>) session.getAttribute("Tasks");
        String authorDescription = request.getParameter("quizDescription");
        java.sql.Date creationDate = new java.sql.Date(System.currentTimeMillis());
        boolean randomizeTasks = request.getParameter("tasksOrder").equals("RandomizedOrder");
        boolean onMultiplePage = request.getParameter("quizAppearence").equals("MultiplePage");
        Quiz quiz = new Quiz(author_id, tasks, authorDescription, creationDate, randomizeTasks, onMultiplePage);
        System.out.println(author_id);
        System.out.println(tasks.size());
        System.out.println(authorDescription);
        System.out.println(creationDate);
        System.out.println(randomizeTasks);
        System.out.println(onMultiplePage);
        quizDAO.addQuiz(quiz);
        response.sendRedirect("/createQuiz/QuizAddedSuccessfully.html");
    }
}
