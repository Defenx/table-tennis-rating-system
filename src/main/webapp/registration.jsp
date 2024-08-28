<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Registration</title>
    <link rel="stylesheet" href="static/css/auth.css">
</head>
<body>
<div class="form-auth">
    <h2 class="auth">Регистрация</h2>
    <form action="/registration" method="post">
        <div class = container-input-content>
            <div class=container-label-input>
                <label for="firstname">Фамилия:</label>
                <input type="text" id="firstname" name="firstname" placeholder="Введите фамилию">
            </div>
            <c:forEach var="firstnameValidationError" items="${firstnameValidationErrors}">
                <p class="validation-message">${firstnameValidationError}</p>
            </c:forEach>
        </div>
        <div class = container-input-content>
            <div class=container-label-input>
                <label for="surname">Имя:</label>
                <input type="text" id="surname" name="surname" placeholder="Введите имя">
            </div>
            <c:forEach var="surnameValidationError" items="${surnameValidationErrors}">
                <p class="validation-message">${surnameValidationError}</p>
            </c:forEach>
        </div>
        <div class = container-input-content>
            <div class=container-label-input>
                <label for="email">Почта:</label>
                <input type="email" id="email" name="email" placeholder="Введите почту">
            </div>
            <c:forEach var="emailValidationError" items="${emailValidationErrors}">
                <p class="validation-message">${emailValidationError}</p>
            </c:forEach>
        </div>
        <div class = container-input-content>
            <div class=container-label-input>
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" placeholder="Введите пароль">
            </div>
            <c:forEach var="passwordValidationError" items="${passwordValidationErrors}">
                <p class="validation-message">${passwordValidationError}</p>
            </c:forEach>
        </div>
        <div class="container-buttons">
            <button type="submit" class="button">Зарегистрироваться</button>
            <button type="button" class="button" onclick="redirectToLogin()">Уже есть аккаунт</button>
        </div>
    </form>
</div>

<script>
    function redirectToLogin() {
        window.location.href = '/login';
    }
</script>
</body>
</html>
