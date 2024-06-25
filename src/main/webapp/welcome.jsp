<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Welcome</title></head>
  <link rel="stylesheet" href="welcome.css">
  <body>
    <div class="welcome-box">
      <h1>Welcome</h1>
      <form>
        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required><br>

        <button type="submit" class="button_default">Login</button>
        <button type="submit" class="button_second">Register</button>
      </form>
    </div>
  </body>
</html>
