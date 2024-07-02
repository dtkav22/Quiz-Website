<%@ page import="Quiz.QuizDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.UserDAO" %>
<%@ page import="User.Performance" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>User Page</title>
    </head>
    <body>
        <%
            String id = (String) session.getAttribute("userId");
            QuizDAO dao = new QuizDAO();
            out.println("Recent Quizzes Field<br>");
            ArrayList<String> quizzes = dao.getQuizzesByDate(10, false);
            for (String quiz_id : quizzes) {
                Quiz cur = dao.getQuiz(quiz_id);
        %>
            <a href = "/quizPage"><%=cur.getQuizName()%></a><br>
        <%}%>

        <%
            out.println("Popular Quizzes Field<br>");
            quizzes = dao.getPopularQuizzes(10);
            for (String quiz_id : quizzes) {
                Quiz cur = dao.getQuiz(quiz_id);
        %>
        <a href = "/quizPage"><%=cur.getQuizName()%></a><br>
        <%}%>

        <%
            out.println("Your Quizzes Field<br>");
            quizzes = dao.getUserQuizzes(id, 10);
            for (String quiz_id : quizzes) {
                Quiz cur = dao.getQuiz(quiz_id);
        %>
        <a href = "/quizPage"><%=cur.getQuizName()%></a><br>
        <%}%>

        <%
            UserDAO userDao = new UserDAO();
            ArrayList<Performance> performances = userDao.getUserPerformanceHistory(id, 10);
            if(!performances.isEmpty()) {

                out.println("Your Performance History<br>");
                for (Performance performance : performances) {
                    Quiz cur = dao.getQuiz(performance.getQuiz_id());
        %>
        <a href = "/quizPage"><%=cur.getQuizName()%></a>
        <%    out.println("Score : " + Double.toString(performance.getScore()) + " ");
              out.println("Date : " + performance.getDate() + "<br>");
                }}%>
        <a href = "/createQuiz">createQuiz</a><br>


        <form action="Logout" method="post">
            <button type="submit">Logout</button>
        </form>


    </body>
</html>
