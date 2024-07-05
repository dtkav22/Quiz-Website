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
</head>
<body>
    <div id="wrapper">

        <div id="content">
            <div id="left-column">
                <%= request.getAttribute("successMessage") %>
            </div>

                <div id="right-column">
            </div>
        </div>

        <div id="footer">
        <p>QuizWebsite</p>
    </div>

</div>
</body>
</html>
