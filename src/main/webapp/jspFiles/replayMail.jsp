<%@ page import="java.sql.SQLException" %>
<%@ page import="User.UserDAO" %>
<%@ page import="User.Mail" %><%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/6/2024
  Time: 11:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Replay</title>
    <link rel="stylesheet" href="../Mail/replayMailStyle.css">
</head>
<body>

<div id="wrapper">
    <div id="content">
        <h1>Mail Detail</h1>
        <div class="mail-detail">
            <%
                session = request.getSession(false);
                String mailId = (String) session.getAttribute("mailId");
                if (mailId != null) {
                    UserDAO dao = new UserDAO();
                    try {
                        Mail mail = dao.getMailById(mailId);
                        String sender_username = dao.getUser(mail.getSender_id()).getUserName();
            %>
            <div class="mail-item">
                <p><strong>From: </strong><%= sender_username
                %></p>
                <p><strong>Date: </strong><%= mail.getSend_date() %></p>
                <p><%= mail.getMail_text() %></p>
            </div>
            <%
                } catch (SQLException e) {
                    out.println("Error retrieving mail: " + e.getMessage());
                }
            } else { %>
            <p>No mail selected.</p>
            <% } %>

            <form action="replay" method="post">
                <div>
                    <label for="mail_text">Message:</label>
                    <textarea id="mail_text" name="mail_text" rows="5" cols="40" required></textarea>
                </div>
                <div>
                    <input type="submit" value="Send">
                </div>
            </form>

        </div>
        <div class="button-container">
            <form action="inbox" method="get">
                <button type="submit" class="back">Back to Inbox</button>
            </form>
        </div>
    </div>
</div>


</body>
</html>
