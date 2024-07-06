<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Pavilion
  Date: 7/6/2024
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Error</title>
  <link rel="stylesheet" href="errorPage.css">
</head>
<body>
<div class="error-container">
  <h1>An Error Occurred</h1>
  <p>Sorry, an unexpected error has occurred. Please try again later.</p>
  <c:if test="${not empty errorMessage}">
    <p><strong>Error Details:</strong> ${errorMessage}</p>
  </c:if>
  <p><a href="${pageContext.request.contextPath}/">Return to Home</a></p>
</div>
</body>
</html>
