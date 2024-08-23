<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="css/authentication.css">
</head>
<body>
<div class="form-auth">
    <h2 class="auth">Registration</h2>
    <form action="/registration" onsubmit="return validateForm()" method="post">
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
                <input name="email" type="text" placeholder="Enter your Email" required>
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
    function validateForm() {
        var email = document.getElementsByName("email")[0].value;
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // var password = document.getElementsByName("password")[0].value;
        // var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{6,}$/;

        if (!emailRegex.test(email)) {
            alert('Please enter a valid email address with "@" and "."');
            return false;
        }
        // if (!passwordRegex.test(password)) {
        //     alert('Password must be at least 6 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.');
        //     return false;
        // }
        return true;
    }

    function redirectToLogin() {
        var isConfirmed = confirm('Are you sure?');

        if (isConfirmed) {
            window.location.href = '/login';
        }
    }
</script>
</body>
</html>
