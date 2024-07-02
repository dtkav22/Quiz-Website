<%@ page import="Quiz.QuizDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.UserDAO" %>
<%@ page import="User.Performance" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="User.User" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script>
        function goToDisplayQuizzes(type) {
            window.location.href = "/displayQuizzes?type=" + type;
        }
    </script>
</head>
<body>
<%
    final int MAX_QUIZZES = 3;
    String id = (String) session.getAttribute("userId");
    QuizDAO dao = new QuizDAO();
    UserDAO userDao = new UserDAO();
%>
<header>HOME PAGE</header>
<div class="container">
    <h1>Welcome to Your Dashboard</h1>

    <div class="half-container">
        <div class="section">
            <div class="section-title" onclick="goToDisplayQuizzes('recent')">Recent Quizzes</div>
            <%
                ArrayList<String> quizzes = dao.getQuizzesByDate(MAX_QUIZZES, false);
                for (String quiz_id : quizzes) {
                    Quiz cur = dao.getQuiz(quiz_id);
            %>
            <div class="quiz-box">
                <a href="quizPage?quiz_id=<%= quiz_id %>"><%= cur.getQuizName() %></a>
                <b>Creation Date: <%=cur.getCreationDate()%></b>
            </div>
            <%}%>
        </div>

        <div class="section">
            <div class="section-title" onclick="goToDisplayQuizzes('popular')">Popular Quizzes</div>
            <%
                quizzes = dao.getPopularQuizzes(MAX_QUIZZES);
                for (String quiz_id : quizzes) {
                    Quiz cur = dao.getQuiz(quiz_id);
            %>
            <div class="quiz-box">
                <a href="quizPage?quiz_id=<%= quiz_id %>"><%= cur.getQuizName() %></a>
                <b>Creation Date: <%=cur.getCreationDate()%></b>
            </div>
            <%}%>
        </div>
    </div>

    <div class="half-container">
        <div class="section">
            <div class="section-title" onclick="goToDisplayQuizzes('yours')">Your Quizzes</div>
            <%
                quizzes = dao.getUserQuizzes(id, MAX_QUIZZES);
                for (String quiz_id : quizzes) {
                    Quiz cur = dao.getQuiz(quiz_id);
            %>
            <div class="quiz-box">
                <a href="quizPage?quiz_id=<%= quiz_id %>"><%= cur.getQuizName() %></a>
                <b>Creation Date: <%=cur.getCreationDate()%></b>
            </div>
            <%}%>
            <%
                if(quizzes.isEmpty()) {
            %>
            <div class="quiz-box">
                <b>You haven't created quiz yet</b>
            </div>
            <%}%>
        </div>
        <div class="section">
            <div class="section-title" onclick="goToDisplayQuizzes('performance')">Your Performance History</div>
            <%
                ArrayList<Performance> performances = userDao.getUserPerformanceHistory(id, MAX_QUIZZES);
                for (Performance performance : performances) {
                    Quiz cur = dao.getQuiz(performance.getQuiz_id());
            %>
            <div class="quiz-box">
                <a href="quizPage?quiz_id=<%= performance.getQuiz_id() %>"><%= cur.getQuizName() %></a>
                <b>Score: <%= performance.getScore() %></b>
                <b>Date: <%= performance.getDate() %></b>
            </div>
            <%}%>
            <%
                if(performances.isEmpty()) {
            %>
            <div class="quiz-box">
                <b>You haven't taken quiz yet</b>
            </div>
            <%}%>
        </div>
    </div>

    <div class="button-container">
        <form action="createQuiz" method="get">
            <button type="submit" class="create">Create Quiz</button>
        </form>
        <form action="Logout" method="post">
            <button type="submit" class="logout">Logout</button>
        </form>
    </div>
</div>
</body>
</html>
