<%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/5/2024
  Time: 5:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mail Sent</title>
    <link rel="stylesheet" href="../Mail/MailSentStyles.css">
</head>
<body>
<div id="wrapper">
    <div id="content">
        <h1>Message Sent</h1>
        <div class="success-message">
            <p>Your mail has successfully been sent.</p>
        </div>
        <div class="links">
            <a href="newMessageForm.jsp">Send Another Message</a>
            <br>
            <a href="displayMails.jsp">Go to Inbox</a>
        </div>
    </div>
</div>

</body>
</html>
