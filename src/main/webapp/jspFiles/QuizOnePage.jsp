<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.*" %>
<%@ page import="java.util.Collections" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.07.2024
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Page</title>
    <link rel="stylesheet" href="../quizPage/styles.css">
</head>
<body>
    <form action="../quizPage" method="post" class="wrapper">
        <ol>
            <%
                ArrayList<QuizTask> tasks = ((ArrayList<QuizTask>)session.getAttribute("tasks"));
                String quiz_id = request.getParameter("quiz_id");
                ArrayList<String> values = (ArrayList<String>) session.getAttribute("values");
                for(int i = 0; i < tasks.size(); i++) {
                    QuizTask quizTask = tasks.get(i);
                    String value = values.get(i);
                    switch (quizTask.getType()) {
                        case TaskTypes.QUESTION_RESPONSE:
                            QuestionResponseTask taskQRT = (QuestionResponseTask)quizTask;
                            out.println("<li>");
                            out.println("<p>Question:" +  taskQRT.getQuestion().getQuestionText() + "</p>");
                            out.println("<label>Answer: <input type=\"text\" name=\"answers\" value = " + value + "></label>");
                            out.println("</li>");
                            break;
                        case TaskTypes.FILL_BLANK:
                            FillBlankTask taskFBT = (FillBlankTask) quizTask;
                            out.println("<li>");
                            out.println("<p>Fill in the blank:</p>");
                            out.println("<p>" + taskFBT.getQuestion().getQuestionText() + " <label><input type=\"text\" name=\"answers\" value = " + value + "></label>");
                            out.println("</li>");
                            break;
                        case TaskTypes.MULTIPLE_CHOICE:
                            MultipleChoiceTask taskMCT = (MultipleChoiceTask) quizTask;
                            String questionText = taskMCT.getQuestion().getQuestionText();
                            Answer answer = taskMCT.getQuestion().getAnswer();
                            ArrayList<String> answers = new ArrayList<String>();
                            for(int j = 0; j < answer.getWrongAnswersNumber(); j++) {
                                answers.add(answer.getWrongAnswerAt(j));
                            }
                            answers.add(answer.getCorrectAnswerAt(0));
                            Collections.shuffle(answers);
                            out.println("<li>");
                            out.println("<p>Question:" + questionText + "</p>");
                            if(value.isEmpty()) value = answers.get(0);
                            for(String text : answers) {
                                if(text.equals(value)) out.println("<label><input type=\"radio\" name=\"answers\" value=\"" + text + "\" autocomplete=\"off\" checked />" + text + "</label>");
                                else out.println("<label><input type=\"radio\" name=\"answers\" value=\"" + text + "\" autocomplete=\"off\" />" + text + "</label>");
                            }
                            out.println("</li>");
                            break;
                        case TaskTypes.PICTURE_RESPONSE:
                            PictureResponseTask taskPRT = (PictureResponseTask) quizTask;
                            out.println("<li>");
                            out.println("<p>Question:" +  taskPRT.getQuestion().getQuestionText() + "</p>");
                            out.println("<img src=\"" + taskPRT.getQuestion().getImgUrl() + "\" alt=\"sorry image does not load\">");
                            out.println("<label>Answer: <input type=\"text\" name=\"answers\" value = " + value + "></label>");
                            out.println("</li>");
                            break;
                    }
                }
            %>
        </ol>
        <input type="hidden" name="quiz_id" value="<%=quiz_id%>">
        <input type="hidden" name="index" value="<%="0"%>">
        <input type="submit" value="To Submit">
    </form>
</body>
</html>
