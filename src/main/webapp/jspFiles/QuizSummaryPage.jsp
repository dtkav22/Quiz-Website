<%@ page import="Quiz.QuizDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.UserDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="User.Performance" %> <%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.07.2024
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QuizSummaryPage</title>
</head>
<body>
    <%
        QuizDAO quizDAO = new QuizDAO();
        UserDAO userDAO = new UserDAO();
        String quiz_id = request.getParameter("quiz_id");
        Quiz quiz = quizDAO.getQuiz(quiz_id);
        String userName = null;
        try {
            userName = userDAO.getUser(quiz.getAuthor_id()).getUserName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>
    <h1><%=quiz.getQuizName()%></h1>
    <h3>Quiz Description: <%=quiz.getAuthorDescription()%></h3>
    <h3>Quiz Creator: <a href = "profilePage?profile_id=<%=quiz.getAuthor_id()%>"><%=userName%></a></h3>
    <label for="performancesOrder">Choose In What Order Your Performances Should Appear: </label>
    <select name="performancesOrder" id="performancesOrder">
        <option value="order=date DESC&quiz_id=<%=quiz_id%>">Order By Date</option>
        <option value="order=score DESC&quiz_id=<%=quiz_id%>">Order By Score</option>
        <option value="order=used_time&quiz_id=<%=quiz_id%>">Order By Used Time</option>
    </select>
    <input type="button" value="Show Performances" onclick="showPerformances()"> <br>
    <ol id = "userPerformances">
        Your Performances:
    </ol>
    <ol>
        Highest Performances:
        <%
            try {
                ArrayList<Performance> performances = userDAO.getHighestPerformersOnQuiz(quiz_id, 5, false);
                for(Performance performance : performances) {
                    String user_name = userDAO.getUser(performance.getUser_id()).getUserName();
                    double score = performance.getScore();
        %>
                    <li>User Name: <a href="/profilePage?profile_id=<%=userDAO.getUserId(user_name)%>"><%=user_name%></a>, User Score: <%=score%></li>
        <%
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        %>
    </ol>
    <ol>
        Top Performers in Last Day:
        <%
            try {
                ArrayList<Performance> performances = userDAO.getHighestPerformersOnQuiz(quiz_id, 5, true);
                for(Performance performance : performances) {
                    String user_name = userDAO.getUser(performance.getUser_id()).getUserName();
                    double score = performance.getScore();
                    String date = performance.getDate();
        %>
        <li>User Name: <a href="/profilePage?profile_id=<%=userDAO.getUserId(user_name)%>"><%=user_name%></a>, User Score: <%=score%>, Date: <%=date%></li>
        <%
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        %>
    </ol>
    <p>
        Average Score on Quiz:
        <%
            double avg;
            try {
                avg = userDAO.getAverageScoreOnQuiz(quiz_id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        %>
        <%=String.format("%.4f", avg)%>
    </p>
    <form method = 'GET' action="../quizPage">
        <input type="hidden" name="quiz_id" value="<%=quiz_id%>">
        <input type="submit" value="Start Quiz">
    </form>
</body>
<script src="../quizPage/script.js"></script>
</html>
