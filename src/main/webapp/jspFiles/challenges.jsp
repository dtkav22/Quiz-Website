<%@ page import="Quiz.QuizDAO" %>
<%@ page import="User.UserDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.User" %>
<%@ page import="User.Challenge" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Challenges</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
</head>
<body>
<%
    final int MAX_DISPLAY = 40;
    String id = (String) session.getAttribute("userId");
    QuizDAO dao = new QuizDAO();
    UserDAO userDao = new UserDAO();
%>
<header>Challenges</header>
<div class="container">
    <h1>Your Dashboard</h1>
    <div class="section">
        <%
            ArrayList<Challenge> challenges = userDao.getChallengesSentForUser(id);
            for (int i = 0; i < (Math.min(challenges.size(), MAX_DISPLAY)); i++) {
                Challenge challenge = challenges.get(i);
                User cur = userDao.getUser(challenge.getUser1_id());
                Quiz quiz = dao.getQuiz(challenge.getQuiz_id());
        %>
        <div class="quiz-box">
            <a href="quizPage?quiz_id=<%= challenge.getQuiz_id() %>"><%= quiz.getQuizName() %></a>
            <a href="profilePage?profile_id=<%= challenge.getUser1_id() %>"><%= "Sent by: " + cur.getUserName() %></a>
            <form action="ChallengeRequest" method="post" style="display:inline;">
                <input type="hidden" name="friend_id" value="<%= challenge.getUser1_id() %>">
                <input type="hidden" name="quiz_id" value="<%= challenge.getQuiz_id() %>">
                <button type="submit" name="action" value="accept" class="accept">Accept</button>
                <button type="submit" name="action" value="reject" class="reject">Reject</button>
            </form>
        </div>
        <%}%>
    </div>
    <div class="button-container">
        <form action="UserHomePage" method="get">
            <button type="submit" class="go-back">Go to Home Page</button>
        </form>
    </div>
</div>
</body>
</html>