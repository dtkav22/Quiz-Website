<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.QuizDAO" %>
<%@ page import="User.UserDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.Performance" %>
<%@ page import="User.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String type = (String) request.getAttribute("type");
    final int MAX_QUIZZES = 50;
    String id = (String) request.getAttribute("profile_id");
    String owner = (id.equals((String)session.getAttribute("userId")) ? "Your" : "User's");
    QuizDAO dao = new QuizDAO();
    UserDAO userDao = new UserDAO();
    ArrayList<Performance> performances = null;
    String header = null;
    if(type.equals("friends")) {
        performances = userDao.getFriendsPerformances(id, MAX_QUIZZES);
        header = "Friends Performance History";
    } else {
        header = owner + " Performance History";
        performances = userDao.getUserPerformanceHistory(id, MAX_QUIZZES);
    }
%>
<head>
    <title><%=header%></title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</head>
<body>
<header><%=header%></header>
<div class="container">
    <h1>Your Dashboard</h1>
    <div class="section">
        <%
            for (Performance performance : performances) {
                Quiz cur = dao.getQuiz(performance.getQuiz_id());
                User user = userDao.getUser(performance.getUser_id());
        %>
        <div class="quiz-box">
            <a href="quizPage?quiz_id=<%= performance.getQuiz_id() %>"><%= cur.getQuizName() %></a>
            <b>Score: <%= performance.getScore() %></b>
            <b>Date: <%= performance.getDate() %></b>
            <%
                if(type.equals("friends")) {
            %>
            <a href="profilePage?profile_id=<%= performance.getUser_id() %>"><%= user.getUserName() %>
        </div>
        <%}}%>
    </div>

    <div class="button-container">
        <button class="go-back" onclick="goBack()">Go Back</button>
    </div>
</div>
</body>
</html>
