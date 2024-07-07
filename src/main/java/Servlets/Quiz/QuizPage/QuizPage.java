package Servlets.Quiz.QuizPage;

import Quiz.QuizDAO;
import Quiz.Quiz;
import Quiz.QuizTask;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
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
            if(!quiz.isOnMultiplePage()) {
                for(int i = 0; i < tasks.size(); i++) {
                    values.add("");
                }
            }
            session.setAttribute("tasks", tasks);
            session.setAttribute("values", values);
            session.setAttribute("startTime", System.currentTimeMillis());
            session.setAttribute("isMultiplePage", quiz.isOnMultiplePage());
        }
        if((boolean)session.getAttribute("isMultiplePage")) {
            request.getRequestDispatcher("/jspFiles/QuizMultiplePage.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/jspFiles/QuizOnePage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String index = request.getParameter("index");
        String quiz_id = request.getParameter("quiz_id");
        if(index != null) {
            ArrayList<QuizTask> tasks = (ArrayList<QuizTask>) session.getAttribute("tasks");
            ArrayList<String> values = (ArrayList<String>) session.getAttribute("values");
            int idx;
            if((boolean)session.getAttribute("isMultiplePage")) {
                idx = Integer.parseInt(index);
                String answer = request.getParameter("answer");
                if(answer == null) answer = "";
                if(values.size() == idx)  {
                    values.add(answer);
                } else {
                    values.set(idx, answer);
                }
                idx++;
            } else {
                idx = tasks.size();
                String[] answers = request.getParameterValues("answers");
                for(int i = 0; i < values.size(); i++) {
                    if(answers[i] == null) answers[i] = "";
                    values.set(i, answers[i]);
                }
            }
            String url = "/quizPage?quiz_id=" + quiz_id + "&index=" + idx;
            if(idx == tasks.size()) url = "/SubmitQuiz?quiz_id=" + quiz_id;
            response.sendRedirect(url);
        } else {
            session.removeAttribute("tasks");
            session.removeAttribute("values");
            session.removeAttribute("startTime");
            session.removeAttribute("isMultiplePage");
            response.sendRedirect("/quizPage?quiz_id=" + quiz_id + "&index=0");
        }
    }
}
