<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Welcome Page</title></head>
  <link rel="stylesheet" href="registerStyle.css">
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

      <script src="registerScript.js"></script>

    </div>
  </body>
</html>
