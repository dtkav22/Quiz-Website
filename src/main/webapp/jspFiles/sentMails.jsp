<%@ page import="User.Mail" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="User.UserDAO" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/6/2024
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sent</title>
    <link rel="stylesheet" href="../Mail/InboxStyle.css">
</head>
<body>
<div id="wrapper">
    <div id="content">
        <h1>Sent Mails</h1>

        <form action="searchSentMails" method="get">
            <label for="search_field">Search your sent mails by username</label>
            <input type="text" id="search_field" name="search_field">

            <button type="submit" id="Search" class="Search">Search</button>
        </form>

        <div class="mail-list">
            <%-- Retrieve mails attribute from session --%>
            <% ArrayList<Mail> mails = (ArrayList<Mail>) session.getAttribute("mails"); %>
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
                    int numReplays;
                    numReplays = replays.size();
                    if(numReplays>1) chat = numReplays +  " replays";
                    else{
                        chat = "";
                    }
                %>
                <li>
                    <div class="mail-item">

                        <form action="openSentMail" method="get">
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
            <form action="inbox" method="get">
                <button type="submit" class="inbox">Back to inbox</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
