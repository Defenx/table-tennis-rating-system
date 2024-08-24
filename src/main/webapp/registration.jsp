<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="css/registration.css">
</head>
<body>
<div class="form-auth">
    <h2 class="auth">Registration</h2>
    <form action="/registration" method="post">
        <div>
            <label>First name
                <input name="firstname" type="text" placeholder="Enter your firstname" required>
            </label>
        </div>
        <div>
            <label>Last name
                <input name="surname" type="text" placeholder="Enter your surname" required>
            </label>
        </div>
        <div>
            <label>Email
                <input name="email" type="email" placeholder="Enter your Email" required>
            </label>
        </div>
        <div>
            <label>Password
                <input name="password" type="password" placeholder="Enter your password" required>
            </label><br>
        </div>
        <div class ="buttons">
            <button type="submit">Register</button>
            or
            <a href="/login" class="link-button" onclick="redirectToLogin()">Back to Login page.</a>
        </div>
    </form>
</div>

<script>
    function redirectToLogin() {
        var isConfirmed = confirm('Are you sure?');

        if (isConfirmed) {
            window.location.href = '/login';
        }
    }
</script>
</body>
</html>
