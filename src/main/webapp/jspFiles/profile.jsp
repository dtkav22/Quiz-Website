<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="User.User" %>
<%@ page import="User.Performance" %>
<%@ page import="User.UserDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizDAO" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Profile Page</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script>
    function goToDisplayQuizzes(type, id) {
            window.location.href = "/displayQuizzes?type=" + type + "&profile_id=" + id;
    }
    </script>
</head>
<body>
<header>Profile Page</header>
<%
    final int MAX_DISPLAY = 3;
    String user_id = (String) session.getAttribute("userId");
    String profile_id = (String) request.getAttribute("profile_id");
    String owner = (profile_id.equals(user_id) ? "Your" : "User's");
    UserDAO userDao = new UserDAO();
    QuizDAO dao = new QuizDAO();
    User cur_user = userDao.getUser(profile_id);
%>
<div class="container">
    <h1>Welcome to <%=owner%> Profile</h1>

    <div class="section">
        <div class="noClick-section-title"><%=owner%> Information</div> <br>
            <div class="quiz-box">
                <hb>Username: </hb>
                <hb><%=cur_user.getUserName()%></hb>
            </div>
            <div class="quiz-box">
                <hb>Mail: </hb>
                <hb><%=cur_user.getEmail()%></hb>
            </div>
    </div>

    <div class="half-container">
    <div class="section">
        <div class="section-title" onclick="goToDisplayQuizzes('yours', <%=profile_id%>)"><%=owner%> Quizzes</div>
        <%
            ArrayList<String> quizzes = dao.getUserQuizzes(profile_id, MAX_DISPLAY);
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
            <b>There is no Quizzes</b>
        </div>
        <%}%>
    </div>
    <div class="section">
        <div class="section-title" onclick="goToDisplayQuizzes('performance', <%=profile_id%>)"><%=owner%> Performance History</div>
        <%
            ArrayList<Performance> performances = userDao.getUserPerformanceHistory(profile_id, MAX_DISPLAY);
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
            <b>There is no performances</b>
        </div>
        <%}%>
    </div>
</div>


</div>


</body>
</html>
