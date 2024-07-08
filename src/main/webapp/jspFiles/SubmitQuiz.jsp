<%@ page import="java.util.ArrayList" %><%--
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
    <link rel="stylesheet" href="../quizPage/styles.css">
</head>
<body>
    <div class = "wrapper">
        <ol>
            <%
                ArrayList<String> values = (ArrayList<String>) session.getAttribute("values");
                for(String value : values) {
                    out.println("<li>");
                    if(value.isEmpty()) {
                        out.println("Not Answered");
                    } else {
                        out.println("Answer Saved");
                    }
                    out.println("</li>");
                }
            %>
        </ol>
        <form action="../SubmitQuiz" method="post">
            <input type="submit" value="Submit Quiz">
            <input type="hidden" name="quiz_id" value="<%=request.getParameter("quiz_id")%>">
        </form>
    </div>
</body>
</html>
