<%@ page import="Quiz.QuizDAO" %>
<%@ page import="User.UserDAO" %>
<%@ page import="User.Mail" %>
<%@ page import="User.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Mails History</title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
</head>
<body>
<%
    final int MAX_DISPLAY = 40;
    String id = (String) session.getAttribute("userId");
    UserDAO userDao = new UserDAO();
%>
<header>Your Mails History</header>
<div class="container">
    <h1>Your Dashboard</h1>
    <div class="section">
        <%
            ArrayList<Mail> mails = userDao.getReceivedMailsForUser(id);
            for (int i = 0; i < (Math.min(mails.size(), MAX_DISPLAY)); i++) {
                Mail mail = mails.get(i);
                User cur = userDao.getUser(mail.getSender_id());
        %>
        <div class="quiz-box">
            <a href="openMail?mailId=<%= mail.getMail_id() %>"><%=mail.getMail_Subject()%></a>
            <a href="profilePage?profile_id=<%= mail.getSender_id() %>"><%= "Sent by: " + cur.getUserName() %></a>
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