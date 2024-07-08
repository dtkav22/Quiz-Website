<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="User.User" %>
<%@ page import="User.Performance" %>
<%@ page import="User.UserDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizDAO" %>
<%@ page import="java.util.ArrayList" %>
<html>
<%
    final int MAX_DISPLAY = 30;
    String user_id = (String) session.getAttribute("userId");
    String profile_id = (String) request.getAttribute("profile_id");
    String owner = (profile_id.equals(user_id) ? "Your" : "User's");
    UserDAO userDao = new UserDAO();
%>
<head>
    <title>Friends List</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script src="../user/script.js"></script>
</head>
<body>
<header><%=owner%> Friends List</header>
<div class="container">
    <h1>Your Dashboard</h1>
    <div class="section">
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
    </div>

    <div class="button-container">
        <form action="UserHomePage" method="get">
            <button type="submit" class="go-back">Go to Home Page</button>
        </form>
    </div>

</div>


</body>
</html>
