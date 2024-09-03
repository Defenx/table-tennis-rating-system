<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Registration</title>
    <link rel="stylesheet" href="static/css/auth.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

</head>
<body>
<div class="form-auth">
    <h2 class="auth">Регистрация</h2>
    <form action="/registration" method="post">
        <div class=container-input-content>
            <div class=container-label-input>
                <label for="firstname">Имя:</label>
                <input type="text" id="firstname" name="firstname" placeholder="Введите имя">
                <div class="input-group-append">
                    <span class="input-group-text" id="tooltip-firstname">
                        <i class="fas fa-question-circle" data-toggle="tooltip" data-placement="right"
                           title="${tooltipFirstName}"></i>
                    </span>
                </div>
            </div>
            <c:forEach var="firstnameValidationError" items="${firstnameValidationErrors}">
                <p class="validation-message">${firstnameValidationError}</p>
            </c:forEach>
        </div>
        <div class=container-input-content>
            <div class=container-label-input>
                <label for="surname">Фамилия:</label>
                <input type="text" id="surname" name="surname" placeholder="Введите фамилию">
                <div class="input-group-append">
                    <span class="input-group-text">
                        <i class="fas fa-question-circle" data-toggle="tooltip" data-placement="right"
                           title="${tooltipFirstName}"></i>
                    </span>
                </div>
            </div>
            <c:forEach var="surnameValidationError" items="${surnameValidationErrors}">
                <p class="validation-message">${surnameValidationError}</p>
            </c:forEach>
        </div>
        <div class=container-input-content>
            <div class=container-label-input>
                <label for="email">Почта:</label>
                <input type="email" id="email" name="email" placeholder="Введите почту">
                <div class="input-group-append">
                    <span class="input-group-text">
                        <i class="fas fa-question-circle" data-toggle="tooltip" data-placement="right"
                           title="${tooltipEmail}"></i>
                    </span>
                </div>
            </div>
            <c:forEach var="emailValidationError" items="${emailValidationErrors}">
                <p class="validation-message">${emailValidationError}</p>
            </c:forEach>
        </div>
        <div class=container-input-content>
            <div class=container-label-input>
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" placeholder="Введите пароль">
                <div class="input-group-append">
                    <span class="input-group-text">
                        <i class="fas fa-question-circle" data-toggle="tooltip" data-placement="right"
                           title="${tooltipPassword}"></i>
                    </span>
                </div>
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
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
<script>
    function redirectToLogin() {
        window.location.href = '/login';
    }

    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    })
</script>
</body>
</html>
