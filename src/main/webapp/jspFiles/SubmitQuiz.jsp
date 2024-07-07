<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.07.2024
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit Quiz</title>
</head>
<body>
  <%=request.getParameter("quiz_id")%>
  <form action="../SubmitQuiz" method="post">
      <input type="submit" value="Submit Quiz">
      <input type="hidden" name="quiz_id" value="<%=request.getParameter("quiz_id")%>">
  </form>
</body>
</html>
