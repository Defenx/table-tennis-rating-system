<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ru">
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Вход в систему</title>
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body>
        <div class="form-auth">
            <h2 class="auth">Авторизация</h2>
            <form action="/login" method="post">
                <div>
                    <label for="email">Почта</label>
                    <input type="text" id="email" name="email" required placeholder="Введите вашу почту">
                    <c:forEach var="emailValidationError" items="${emailValidationErrors}">
                        <p class="validation-message">${emailValidationError}</p>
                    </c:forEach>
                </div>
                <div>
                    <label for="password">Пароль</label>
                    <input type="password" id="password" name="password" required placeholder="Введите ваш пароль">
                    <c:forEach var="passwordValidationError" items="${passwordValidationErrors}">
                        <p class="validation-message">${passwordValidationError}</p>
                    </c:forEach>
                </div>
                <div class="buttons">
                    <button type="submit">Войти</button>
                    <a href="/registration" class="link-button">Регистрация</a>
                </div>
            </form>
        </div>
    </body>
</html>