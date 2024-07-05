<%@ page import="Quiz.QuizDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="User.UserDAO" %>
<%@ page import="java.sql.SQLException" %> <%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.07.2024
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QuizSummaryPage</title>
</head>
<body>
    <%
        QuizDAO quizDAO = new QuizDAO();
        UserDAO userDAO = new UserDAO();
        Quiz quiz = quizDAO.getQuiz(request.getParameter("quiz_id"));
        String userName = null;
        try {
            userName = userDAO.getUser(quiz.getAuthor_id()).getUserName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>
    <%= userName%>

</body>
</html>
