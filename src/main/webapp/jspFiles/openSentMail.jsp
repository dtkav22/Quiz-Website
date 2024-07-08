<%@ page import="java.sql.SQLException" %>
<%@ page import="User.Mail" %>
<%@ page import="User.UserDAO" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/6/2024
  Time: 2:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mail</title>
    <link rel="stylesheet" href="../Mail/readMailStyle.css">
</head>
<body>

<div id="wrapper">
    <div id="content">
        <h1>Mail Detail</h1>
        <div class="mail-detail">
            <%
                ArrayList<Mail> replaySet = (ArrayList<Mail>) session.getAttribute("replaySet");
                String sender_username = "";
                UserDAO dao = new UserDAO();
                    if(replaySet.size()>1){
                        for(Mail mail : replaySet){
                            try {
                                sender_username = dao.getUser(mail.getSender_id()).getUserName();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
            %>
            <div class="mail-item">
                <p><strong>From: </strong><%= sender_username
                %></p>
                <p><strong>Date: </strong><%= mail.getSend_date() %></p>
                <p><%= mail.getMail_text() %></p>
            </div>
            <%
                        }
                    } else if(replaySet.size() == 1){
                        Mail mail = replaySet.get(0);
                String receiver_username;
                try {
                    receiver_username = dao.getUser(mail.getReceiver_id()).getUserName();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            %>
            <div class="mail-item">
                <p><strong>To: </strong><%= receiver_username
                %></p>
                <p><strong>Date: </strong><%= mail.getSend_date() %></p>
                <p><%= mail.getMail_text() %></p>
            </div>
            <%
                        } else { %>
            <p>No mail selected.</p>
            <% } %>
        </div>
        <div class="button-container">
            <form action="replay" method="get">
                <button type="submit" class="replay">Replay</button>
            </form>
            <form action="inbox" method="get">
                <button type="submit" class="back">Back to Inbox</button>
            </form>
            <form action="deleteMail" method="post">
                <button type="submit" class="delete">Delete this mail</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
