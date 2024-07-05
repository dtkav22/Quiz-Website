<%@ page import="Quiz.QuizDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.UserDAO" %>
<%@ page import="User.Performance" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="User.User" %>
<%@ page import="User.Challenge" %>
<%@ page import="User.Mail" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="../user/script.js"></script>
</head>
<body>
<%
    final int MAX_DISPLAY = 3;
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
                ArrayList<String> quizzes = dao.getQuizzesByDate(MAX_DISPLAY, false);
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
                quizzes = dao.getPopularQuizzes(MAX_DISPLAY);
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

        <div class="section" id="friendsPerformance">
            <div class="section-title" onclick="goToDisplayQuizzes('friends')">Friends Performance History</div>
            <%
                ArrayList<Performance> performances = userDao.getFriendsPerformances(id, MAX_DISPLAY);
                for (Performance performance : performances) {
                    Quiz cur = dao.getQuiz(performance.getQuiz_id());
                    User user = userDao.getUser(performance.getUser_id());
            %>
            <div class="quiz-box">
                <a href="quizPage?quiz_id=<%= performance.getQuiz_id() %>"><%= cur.getQuizName() %></a>
                <b>Score: <%= performance.getScore() %></b>
                <b>Date: <%= performance.getDate() %></b>
                <a href="profilePage?profile_id=<%= performance.getUser_id() %>"><%= user.getUserName() %></a>
            </div>
            <%}%>
            <%
                if(performances.isEmpty()) {
            %>
            <div class="quiz-box">
                <b>Your friends haven't taken quiz yet</b>
            </div>
            <%}%>
        </div>

        <div class="section">
            <div class="section-title" onclick="goToDisplayRequests('mails')">Mails</div>
            <%
                ArrayList<Mail> mails = userDao.getReceivedMailsForUser(id);
                for (int i = 0; i < (Math.min(mails.size(), MAX_DISPLAY)); i++) {
                    Mail mail = mails.get(i);
                    User cur = userDao.getUser(mail.getSender_id());
            %>
            <div class="quiz-box">
                <a href="mailPage?mail_id=<%= mail.getMail_id() %>"><%=mail.getMail_subject()%></a>
                <a href="profilePage?profile_id=<%= mail.getSender_id() %>"><%= "Sent by: " + cur.getUserName() %></a>
            </div>
            <%}%>
            <%
                if(mails.isEmpty()) {
            %>
            <div class="quiz-box">
                <b>You haven't received any mails</b>
            </div>
            <%}%>
        </div>


    </div>

    <div class="half-container">

        <div class="section" id="friendsRequest">
            <div class="section-title" onclick="goToDisplayRequests('friends')">Friend Request</div>
            <%
                ArrayList<String> list = userDao.getFriendRequestsForUser(id);
                for (int i = 0; i < (Math.min(list.size(), MAX_DISPLAY)); i++) {
                    String friend_id = list.get(i);
                    User cur = userDao.getUser(friend_id);
            %>
            <div class="quiz-box" id="quiz-box-<%= friend_id %>">
                <a href="profilePage?profile_id=<%= friend_id %>"><%= cur.getUserName() %></a>
                <form style="display:inline;">
                    <button type="button" class="accept" onclick="handleFriendRequest(true, 'accept', '<%=friend_id%>')">Accept</button>
                    <button type="button" class="reject" onclick="handleFriendRequest(true, 'reject', '<%=friend_id%>')">Reject</button>
                </form>
            </div>
            <%}%>
            <%
                if(list.isEmpty()) {
            %>
            <div class="quiz-box">
                <b>You have no friends request</b>
            </div>
            <%}%>
        </div>

        <div class="section" id="challengeRequest">
            <div class="section-title" onclick="goToDisplayRequests('challenges')">Challenges Request</div>
            <%
                ArrayList<Challenge> challenges = userDao.getChallengesSentForUser(id);
                for (int i = 0; i < (Math.min(challenges.size(), MAX_DISPLAY)); i++) {
                    Challenge challenge = challenges.get(i);
                    User cur = userDao.getUser(challenge.getUser1_id());
                    Quiz quiz = dao.getQuiz(challenge.getQuiz_id());
                    String friend_id = challenge.getUser1_id();
                    String quiz_id = challenge.getQuiz_id();
            %>
            <div class="quiz-box">
                <a href="quizPage?quiz_id=<%= challenge.getQuiz_id() %>"><%= quiz.getQuizName() %></a>
                <a href="profilePage?profile_id=<%= challenge.getUser1_id() %>"><%= "Sent by: " + cur.getUserName() %></a>
                <form style="display:inline;">
                    <button type="button" class="accept" onclick="handleChallengeRequest('-1','accept', <%=friend_id%>, <%=quiz_id%>)">Accept</button>
                    <button type="button" class="reject" onclick="handleChallengeRequest('-1','accept', <%=friend_id%>, <%=quiz_id%>)">Reject</button>
                </form>
            </div>
            <%}%>
            <%
                if(challenges.isEmpty()) {
            %>
            <div class="quiz-box">
                <b>You have no challenges request</b>
            </div>
            <%}%>
        </div>


    </div>

    <div class="button-container">
        <form action="createQuiz" method="get">
            <button type="submit" class="create">Create Quiz</button>
        </form>
        <form action="profilePage" method="get">
            <input type="hidden" name="profile_id" value=<%=id%>>
            <button type="submit" class="create">Go to Profile Page</button>
        </form>
        <form action="Logout" method="post">
            <button type="submit" class="logout">Logout</button>
        </form>

    </div>
</div>
</body>
</html>
