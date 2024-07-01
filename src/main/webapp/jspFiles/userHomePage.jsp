<%@ page import="Quiz.QuizDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.Quiz" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/26/2024
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
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
            quizzes = dao.getQuizzesByDate(10, false);
            for (String quiz_id : quizzes) {
                Quiz cur = dao.getQuiz(quiz_id);
        %>
        <a href = "/quizPage"><%=cur.getQuizName()%></a><br>
        <%}%>
        <%
            quizzes = dao.getUserQuizzes(id, 10);
            if(!quizzes.isEmpty()) {

                out.println("Your Field<br>");
                for (String quiz_id : quizzes) {
                    Quiz cur = dao.getQuiz(quiz_id);
        %>
        <a href = "/quizPage"><%=cur.getQuizName()%></a><br>
        <%}}%>
        <a href = "/createQuiz">createQuiz</a><br>
    </body>
</html>
