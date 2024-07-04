<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.QuizDAO" %>
<%@ page import="User.UserDAO" %>
<%@ page import="Quiz.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String type = (String) request.getAttribute("type");
    final int MAX_QUIZZES = 20;
    String id = (String) request.getAttribute("profile_id");
    String owner = (id.equals((String)session.getAttribute("userId")) ? "Your" : "User's");
    QuizDAO dao = new QuizDAO();
    UserDAO userDao = new UserDAO();
    ArrayList<String> quizzes = null;
    String header = null;
    if(type.equals("recent")) {
        quizzes = dao.getQuizzesByDate(MAX_QUIZZES, false);
        header = "Recent Quizzes";
    } else if(type.equals("popular")) {
        quizzes = dao.getPopularQuizzes(MAX_QUIZZES);
        header = "Popular Quizzes";
    } else if(type.equals("yours")) {
        quizzes = dao.getUserQuizzes(id, MAX_QUIZZES);
        header = owner + " Quizzes";
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid type parameter");
    }
%>
<head>
    <title><%=header%></title>
    <link rel="stylesheet" type="text/css" href="../user/userhome.css">
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</head>
<body>
<header><%=header%></header>
<div class="container">
    <h1>Your Dashboard</h1>
    <div class="section">
        <%
            for (String quiz_id : quizzes) {
                Quiz cur = dao.getQuiz(quiz_id);
        %>
        <div class="quiz-box">
            <a href="quizPage?quiz_id=<%= quiz_id %>"><%= cur.getQuizName() %></a>
            <b>Creation Date: <%=cur.getCreationDate()%></b>
        </div>
        <%}%>
    </div>

    <div class="button-container">
        <button class="go-back" onclick="goBack()">Go Back</button>
    </div>

</div>
</body>
</html>
