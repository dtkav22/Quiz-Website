<%@ page import="User.Mail" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="User.UserDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="User.User" %>
<%@ page import="java.sql.Date" %><%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/5/2024
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search results</title>
    <link rel="stylesheet" href="../Mail/InboxStyle.css">
</head>
<body>
<div id="wrapper">
    <div id="content">
        <h1>Search results</h1>

        <form action="searchSentMails" method="get">

            <label for="search_field">Search mails in your inbox by username</label>
            <input type="text" id="search_field" name="search_field">

            <button type="submit" id="Search" class="Search">Search</button>
        </form>

        <div class="mail-list">
            <%-- Retrieve mails attribute from session --%>
            <% ArrayList<Mail> mails = (ArrayList<Mail>) session.getAttribute("search"); %>
            <% if (mails != null && !mails.isEmpty()) { %>
            <ul>
                <% for (Mail mail : mails) {
                    UserDAO dao = new UserDAO();
                    String receiver_username;
                    try {
                        receiver_username = dao.getUser(mail.getReceiver_id()).getUserName();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    String Subject = mail.getMail_Subject();
                    if(Subject == null) Subject = "";
                    String chat;
                    ArrayList<Mail> replays;
                    try {
                        replays = dao.getReplaysForMail(mail.getMail_id());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    session.setAttribute("replaySet", replays);
                    int numReplays;
                    try {
                        numReplays = dao.getReplaysForMail(mail.getMail_id()).size();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if(numReplays>1) chat = numReplays +  " replays";
                    else{
                        chat = "";
                    }
                %>
                <li>
                    <div class="mail-item">

                        <form action="openMail" method="get">
                            <button type="submit" class="mail-link">
                                <input type="hidden" name="mailId" value="<%= mail.getMail_id() %>">

                                <p><strong><%= Subject %></strong></p>
                                <p><strong>To: </strong><%= receiver_username %></p>
                                <p><strong>Date: </strong><%= mail.getSend_date() %>
                                    <strong>Time: </strong><%= mail.getSend_time() %></p>
                                <p><%= chat %></p>

                            </button>
                        </form>
                    </div>
                </li>
                <% } %>
            </ul>
            <% } else { %>
            <p>No mails here.</p>
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
            <form action="inbox" method="get">
                <button type="submit" class="inbox">Go to inbox</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
