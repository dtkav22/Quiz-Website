<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.QuizDAO" %>
<%@ page import="User.UserDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.Performance" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String type = (String) session.getAttribute("type");
    final int MAX_QUIZZES = 50;
    String id = (String) session.getAttribute("userId");
    QuizDAO dao = new QuizDAO();
    UserDAO userDao = new UserDAO();
    ArrayList<Performance> performances = userDao.getUserPerformanceHistory(id, MAX_QUIZZES);
%>
<head>
    <title>Your Performance History</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
</head>
<body>
<header>Your Performance History</header>
<div class="container">
    <h1>Your Dashboard</h1>
    <div class="section">
        <%
            for (Performance performance : performances) {
                Quiz cur = dao.getQuiz(performance.getQuiz_id());
        %>
        <div class="quiz-box">
            <a href="quizPage?quiz_id=<%= performance.getQuiz_id() %>"><%= cur.getQuizName() %></a>
            <b>Score: <%= performance.getScore() %></b>
            <b>Date: <%= performance.getDate() %></b>
        </div>
        <%}%>
    </div>

    <div class="button-container">
        <form action="UserHomePage" method="get">
            <button type="submit" class="go-back">Go Back</button>
        </form>
    </div>
</div>
</body>
</html>
