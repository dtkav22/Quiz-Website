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
        <div id="left-column">
            <div class="homepage-section">
                <div class="title">Notes</div>
                <%
                    String userID = (String) request.getSession().getAttribute("userID");
                    ArrayList<Mail> mails = null;
                    try {
                        UserDAO dao = new UserDAO();
                        mails = dao.getReceivedMailsForUser(userID);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    for (Mail mail : mails) {
                        out.println("<div class=\"mail\">");
                        out.println("</div>");
                        out.println("Date: " + mail.getSend_date());
                        out.println("</div>");
                        out.println("<div>");
                        out.println("From: " + mail.getSender_id());
                        out.println("<div>");
                        out.println("</div>");
                        out.println("Message: " + mail.getMail_text());
                        out.println("</div>");
                        out.println("</div>");
                    }
                %>
            </div>
        </div>

        <div id="right-column">
        </div>
    </div>

</div>
</body>
</html>
