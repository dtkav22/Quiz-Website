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
    <title>Display Mails</title>
    <link rel="stylesheet" href="../Mail/InboxStyle.css">
</head>
<body>
<div id="wrapper">
    <div id="content">
        <h1>Inbox</h1>
        <div class="mail-list">
            <%-- Retrieve mails attribute from session --%>
            <% ArrayList<Mail> mails = (ArrayList<Mail>) session.getAttribute("mails"); %>
            <% if (mails != null && !mails.isEmpty()) { %>
            <ul>
                <% for (Mail mail : mails) {
                    UserDAO dao = new UserDAO();
                    String sender_username;
                    try {
                        sender_username = dao.getUser(mail.getSender_id()).getUserName();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    String Subject = mail.getMail_Subject();
                    if(Subject == null) Subject = "";
                %>
                <li>
                    <div class="mail-item">

                        <form action="openMail" method="get">
                            <button type="submit" class="mail-link">
                                <input type="hidden" name="mailId" value="<%= mail.getMail_id() %>">

                                <p><strong>From: </strong><%= sender_username %></p>
                                <p><strong>Date: </strong><%= mail.getSend_date() %></p>
                                <p><%= Subject %></p>

                            </button>
                        </form>
                    </div>
                </li>
                <% } %>
            </ul>
                <% } else { %>
            <p>You have no mails yet.</p>
            <%}%>
        </div>
        <div class="button-container">
            <form action="sendMessage" method="get">
                <button type="submit" class="newMessage">Send New Message</button>
            </form>
            <form action="UserHomePage" method="get">
                <button type="submit" class="homepage">Go to homepage</button>
            </form>
            <form action="sentMails" method="get">
                <button type="submit" class="sentMails">Go to sent mails</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>