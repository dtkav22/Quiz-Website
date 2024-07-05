<%@ page import="User.Mail" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="User.UserDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="User.User" %><%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/5/2024
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inbox</title>
</head>
<body>
<div id="wrapper">
    <div id="content">
        <h1>Inbox</h1>
        <div id="inbox-messages">
            <%
                String userID = (String) request.getSession().getAttribute("user_id");
                UserDAO dao = new UserDAO();
                ArrayList<Mail> mails = null;
                try {
                    mails = dao.getReceivedMailsForUser(userID);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (mails.isEmpty()) {
            %>
            <div class="no-messages">You have no messages.</div>
            <%
            } else {
                for (Mail mail : mails) {
            %>
            <div class="mail">
                <div class="mail-header">
                    <span class="mail-date">Date: <%= mail.getSend_date() %></span>
                    <span class="mail-from">From: <%= mail.getSender_id() %></span>
                </div>
                <div class="mail-body">
                    <p><%= mail.getMail_text() %></p>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</div>
</body>
</html>
