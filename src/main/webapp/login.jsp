<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Вход в систему</title>
    <link rel="stylesheet" href="static/css/auth.css">
    <style>

    </style>
</head>
<body>
<div class="form-auth">
    <h2 class="auth">Войти</h2>
    <form action="/login" method="post">
        <div class="error-container" id="errorContainer">
            <p>${loginError}</p>
        </div>
        <div class="container-input-content">
            <div class="container-label-input">
                <label for="email">Почта:</label>
                <input type="text" id="email" name="email" required placeholder="Введите почту">
            </div>
            <c:forEach var="emailValidationError" items="${emailValidationErrors}">
                <p class="validation-message">${emailValidationError}</p>
            </c:forEach>
        </div>
        <div class="container-input-content">
            <div class="container-label-input">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required placeholder="Введите пароль">
            </div>
            <c:forEach var="passwordValidationError" items="${passwordValidationErrors}">
                <p class="validation-message">${passwordValidationError}</p>
            </c:forEach>
        </div>
        <div class="container-buttons">
            <button type="submit" class="button">Войти</button>
            <button type="button" class="button" onclick="redirectToRegistration()">Регистрация</button>
        </div>
    </form>
</div>
<script>
    function redirectToRegistration() {
        window.location.href = "/registration";
    }

    window.onload = function () {
        let errorContainer = document.getElementById('errorContainer');
        if ("${loginError}" !== "") {
            errorContainer.style.display = 'block';
        }
    }
</script>
</body>
</html>