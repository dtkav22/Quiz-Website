<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/26/2024
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>

<%=
session.getAttribute("username")
%>

</body>
</html>
