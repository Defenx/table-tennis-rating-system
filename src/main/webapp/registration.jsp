<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

<div>
    <fieldset>
        <h3>Registration</h3>
        <form action="/registration" onsubmit="return validateForm()" method="post">
            <label>First name:
                <input name="firstname" type="text">
            </label><br><br>
            <label>Last name:
                <input name="lastname" type="text">
            </label><br><br>
            <label>Email:
                <input name="email" type="text">
            </label><br><br>
            <label>Password:
                <input name="password" type="password">
            </label><br><br>
            <button type="submit">Register</button>
            or
            <a href="/login" class="button" onclick="redirectToLogin()">Back to Login page.</a>
        </form>
    </fieldset>
</div>

</body>
<script>
    function validateForm() {
        var email = document.getElementsByName("email")[0].value;
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        var password = document.getElementsByName("password")[0].value;
        var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{6,}$/; // Регулярное выражение для проверки пароля

        if (!emailRegex.test(email)) {
            alert('Please enter a valid email address with "@" and "."');
            return false;
        }
        if (!passwordRegex.test(password)) {
            alert('Password must be at least 6 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.');
            return false;
        }
        return true;
    }

    function redirectToLogin() {
        var isConfirmed = confirm('Are you sure?');

        if (isConfirmed) {
            window.location.href = '/login';
        }
    }
</script>
</html>

