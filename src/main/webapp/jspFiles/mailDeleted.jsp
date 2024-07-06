<%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/6/2024
  Time: 11:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mail Deleted</title>
    <link rel="stylesheet" href="../Mail/MailDeleted.css">
</head>
<body>
<div id="wrapper">
  <div id="content">
    <h1>Message Sent</h1>
    <div class="delete-message">
      <p>Mail deleted successfully.</p>
    </div>
    <div class="button-container">
      <form action="inbox" method="get">
        <button type="submit" class="back">Back to inbox</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
