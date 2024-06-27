<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.06.2024
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>createQuiz</title>
</head>
<body>
    <h1>Tasks:</h1>
    <div id = "TasksWrapper">
        <ol>

        </ol>
    </div>
    <form action = 'addTask' method = 'GET'>
        <label for = "TaskType">Choose Task Type: </label>
        <select name = "TaskType" id = "TaskType">
            <option value = "FillBlankTask">FillBlankTask</option>
            <option value = "MultipleChoiceTask">MultipleChoiceTask</option>
            <option value = "QuestionResponseTask">QuestionResponseTask</option>
            <option value = "PictureResponseTask">PictureResponseTask</option>
        </select>
        <input type = "submit" value = "Add Task">
    </form>
</body>
</html>
