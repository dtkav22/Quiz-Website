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
    <style>
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="../user/script.js"></script>
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
    boolean areFriends = false;
    if(!profile_id.equals(user_id)) {
        areFriends = userDao.areFriends(user_id, profile_id);
    }
%>
<div class="container">
    <h1>Welcome to Your Dashboard</h1>

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
            <%
                if(!profile_id.equals(user_id)) {
            %>
            <div class="quiz-box">
                <hb>Status: </hb>
                <hb><%=(areFriends ? "Friends" : "Not Friends")%></hb>
            </div>

            <%
                if(userDao.canSendFriendRequest(user_id, profile_id)) {
            %>
            <div id="sendFriendRequest">
                <form style="display:inline;">
                <button type="button" class="accept" onclick="sendFriendRequest('<%=profile_id%>')">Send Friend Request</button>
                </form>
            </div>
            <%} else if(userDao.canAcceptFriendRequest(profile_id, user_id)) {%>
            <div id="quiz-box-<%=profile_id%>">
                <form style="display:inline;">
                    <button type="button" class="accept" onclick="handleFriendRequest(false, 'accept', '<%=profile_id%>')">Accept Friend Request</button>
                    <button type="button" class="reject" onclick="handleFriendRequest(false, 'reject', '<%=profile_id%>')">Reject Friend Request</button>
                </form>
            </div>
        <%}}%>
    </div>

    <div class="half-container">
    <div class="section">
        <div class="section-title" onclick="goToDisplayQuizzes('yours', '<%=profile_id%>')"><%=owner%> Quizzes</div>
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
        <div class="section-title" onclick="goToDisplayQuizzes('performance', '<%=profile_id%>')"><%=owner%> Performance History</div>
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

    <div class="half-container">
        <div class="section">
            <div class="section-title" onclick="goToFriends('<%=profile_id%>')">Friends List</div>
            <%
                ArrayList<String> friends = userDao.getFriendsForUser(profile_id);
                for(int i = 0; i < Math.min(friends.size(), MAX_DISPLAY); i++) {
                    String friend_id = friends.get(i);
                    User cur = userDao.getUser(friend_id);
            %>
            <div class="quiz-box">
                <a href="profilePage?profile_id=<%= friend_id %>"><%=cur.getUserName() %></a>
            </div>
            <%}%>
            <%
                if(friends.isEmpty()) {
            %>
            <div class="quiz-box">
                <b><%=owner%> Friends List is Empty</b>
            </div>
            <%}%>
        </div>

        <div class="section">
            <div class="noClick-section-title">Search Other User</div>
                <input type="text" id="fusername" name="fusername">
                <button type="button" class="go-back" onclick="sendToLogin()">Search</button>
        </div>

    </div>


    <div class="button-container">
        <form action="UserHomePage" method="get">
            <button type="submit" class="go-back">Go to Home Page</button>
        </form>
    </div>

</div>


</body>
</html>
