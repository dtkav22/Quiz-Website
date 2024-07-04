<%@ page import="Quiz.QuizDAO" %>
<%@ page import="User.UserDAO" %>
<%@ page import="User.Mail" %>
<%@ page import="User.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friend Requests</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
</head>
<body>
<%
    final int MAX_DISPLAY = 40;
    String id = (String) session.getAttribute("userId");
    UserDAO userDao = new UserDAO();
%>
<header>Friend Requests</header>
<div class="container">
    <h1>Your Dashboard</h1>
    <div class="section">
        <%
            ArrayList<String> list = userDao.getFriendRequestsForUser(id);
            for (int i = 0; i < (Math.min(list.size(), MAX_DISPLAY)); i++) {
                String friend_id = list.get(i);
                User cur = userDao.getUser(friend_id);
        %>
        <div class="quiz-box">
            <a href="profilePage?profile_id=<%= friend_id %>"><%= cur.getUserName() %></a>
            <form action="FriendRequest" method="post" style="display:inline;">
                <input type="hidden" name="friend_id" value="<%= friend_id %>">
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