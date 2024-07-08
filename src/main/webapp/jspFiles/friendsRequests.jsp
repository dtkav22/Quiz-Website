<%@ page import="User.UserDAO" %>
<%@ page import="User.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friend Requests</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="../user/script.js"></script>
    <script src="../user/Socket.js"></script>
    <script>
        function getFriendRequests(sender_id) {
            window.location.reload(true);
        }
    </script>
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
        <div class="quiz-box" id="quiz-box-<%= friend_id %>">
            <a href="profilePage?profile_id=<%= friend_id %>"><%= cur.getUserName() %></a>
            <form style="display:inline;">
                <button type="button" class="accept" onclick="handleFriendRequest(false, 'accept', '<%=friend_id%>')">Accept</button>
                <button type="button" class="reject" onclick="handleFriendRequest(false, 'reject', '<%=friend_id%>')">Reject</button>
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