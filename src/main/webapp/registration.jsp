<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Registration</title>
    <link rel="stylesheet" href="static/css/auth.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

</head>
<body>
<div class="form-auth">
    <h2 class="auth">Регистрация</h2>
    <form action="/registration" method="post">

        <div class="container-input-content">
            <div class="container-label-input">
                <label for="firstname">Имя:</label>
                <div class="input-wrapper">
                    <input type="text" id="firstname" name="firstname" placeholder="Введите имя">
                    <span class="input-icon">
                        <i class="fas fa-question-circle" data-placement="right"
                           title="${tooltipFirstName}"></i>
                    </span>
                </div>


            </div>
            <c:if test="${not empty firstnameValidationErrors}">
                <div>
                    <c:forEach var="firstnameValidationError" items="${firstnameValidationErrors}">
                        <p class="validation-message">${firstnameValidationError}</p>
                    </c:forEach>
                </div>
            </c:if>
        </div>

        <div class=container-input-content>
            <div class=container-label-input>
                <label for="surname">Фамилия:</label>
                <div class="input-wrapper">
                    <input type="text" id="surname" name="surname" placeholder="Введите фамилию">
                    <span class="input-icon">
                        <i class="fas fa-question-circle" data-placement="right"
                           title="${tooltipSurname}"></i>
                    </span>
                </div>
            </div>
        </div>
        <c:if test="${not empty surnameValidationErrors}">
            <div>
                <c:forEach var="surnameValidationError" items="${surnameValidationErrors}">
                    <p class="validation-message">${surnameValidationError}</p>
                </c:forEach>
            </div>
        </c:if>

        <div class=container-input-content>
            <div class=container-label-input>
                <label for="email">Почта:</label>
                <div class="input-wrapper">
                    <input type="email" id="email" name="email" placeholder="Введите почту">
                    <span class="input-icon">
                        <i class="fas fa-question-circle" data-placement="right"
                           title="${tooltipEmail}"></i>
                    </span>
                </div>
            </div>
        </div>
        <c:if test="${not empty emailValidationErrors}">
            <div>
                <c:forEach var="emailValidationError" items="${emailValidationErrors}">
                    <p class="validation-message">${emailValidationError}</p>
                </c:forEach>
            </div>
        </c:if>

        <div class=container-input-content>
            <div class=container-label-input>
                <label for="password">Пароль:</label>
                <div class="input-wrapper">
                    <input type="password" id="password" name="password" placeholder="Введите пароль">
                    <span class="input-icon">
                        <i class="fas fa-question-circle" data-placement="right"
                           title="${tooltipPassword}"></i>
                    </span>
                </div>
            </div>

        </div>
        <c:if test="${not empty passwordValidationErrors}">
            <div>
                <c:forEach var="passwordValidationError" items="${passwordValidationErrors}">
                    <p class="validation-message">${passwordValidationError}</p>
                </c:forEach>
            </div>
        </c:if>

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
