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
            <label for="firstname">First name:</label>
            <input type="text" id="firstname" name="firstname" placeholder="Enter your firstname">
            <c:forEach var="firstnameValidationError" items="${firstnameValidationErrors}">
                <p class="validation-message">${firstnameValidationError}</p>
            </c:forEach>
        </div>
        <div>
            <label for="surname">Surname:</label>
            <input type="text" id="surname" name="surname" placeholder="Enter your surname">
            <c:forEach var="surnameValidationError" items="${surnameValidationErrors}">
                <p class="validation-message">${surnameValidationError}</p>
            </c:forEach>
        </div>
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="Enter your Email">
            <c:forEach var="emailValidationError" items="${emailValidationErrors}">
                <p class="validation-message">${emailValidationError}</p>
            </c:forEach>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password"  placeholder="Enter your password">
            <c:forEach var="passwordValidationError" items="${passwordValidationErrors}">
                <p class="validation-message">${passwordValidationError}</p>
            </c:forEach>
        </div>
        <div class ="buttons">
            <button type="submit">Register</button>
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
