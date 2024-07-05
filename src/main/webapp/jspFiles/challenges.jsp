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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="../user/script.js"></script>
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
                String box_id = Integer.toString(i);
                String friend_id = challenge.getUser1_id();
                String quiz_id = challenge.getQuiz_id();
        %>
        <div class="quiz-box" id=quiz-box-<%=box_id%>>
            <a href="quizPage?quiz_id=<%= challenge.getQuiz_id() %>"><%= quiz.getQuizName() %></a>
            <a href="profilePage?profile_id=<%= challenge.getUser1_id() %>"><%= "Sent by: " + cur.getUserName() %></a>
            <form style="display:inline;">
                <button type="button" class="accept" onclick="handleChallengeRequest(<%=box_id%>,'accept', <%=friend_id%>, <%=quiz_id%>)">Accept</button>
                <button type="button" class="reject" onclick="handleChallengeRequest(<%=box_id%>,'accept', <%=friend_id%>, <%=quiz_id%>)">Reject</button>
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