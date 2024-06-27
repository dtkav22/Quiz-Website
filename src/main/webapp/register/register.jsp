<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Register Page</title></head>
<link rel="stylesheet" href="registerStyle.css">
<body>
<div class="welcome-box">
    <h1>Create Account</h1>
    <form>
        <label for="email">Email</label>
        <input type="text" id="email" name="email" required>

        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <button type="submit" class="register_button">Register</button>
    </form>

    <script src="registerScript.js"></script>

</div>
</body>
</html>
