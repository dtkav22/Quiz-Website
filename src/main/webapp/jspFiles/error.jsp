<%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/6/2024
  Time: 5:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User does not exist</title>
    <link rel="stylesheet" href="../Mail/errorStyle.css">
</head>
<body>

<div id="wrapper">
    <div id="content">
        <h1>User does not exist</h1>
        <div class="error-message">
            <p>The user you wanted to send an email to does not exist.</p>
        </div>
        <div class="button-container">
            <form action="sendMessage" method="get">
                <button type="submit" class="newMessage">Try again</button>
            </form>
            <form action="inbox" method="get">
                <button type="submit" class="inbox">Go to inbox</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
