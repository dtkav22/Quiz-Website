<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Page</title>
    <link rel="stylesheet" href="../welcome/styles.css">
</head>
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

            <button type="submit" id="register_button2" class="register_button2">Register</button>
        </form>
        <div id="error"></div>
    </div>
</body>
<script src="../welcome/registerScript.js"></script>
</html>