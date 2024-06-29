<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Register Page</title>
  <link rel="stylesheet" href="../../welcome/styles.css">
</head>
<body>
<div class="welcome-box">
  <h1>Welcome</h1>
  <form>
    <label for="username">Username</label>
    <input type="text" id="username" name="username" required>

    <label for="password">Password</label>
    <input type="password" id="password" name="password" required>

    <button type="submit" id="login_button" class="login_button">Login</button>
    <button type="button" id="register_button" class="register_button">Register</button>
  </form>
  <div id="error"></div>
</div>
</body>
<script src="../../welcome/welcome.js"></script>
</html>