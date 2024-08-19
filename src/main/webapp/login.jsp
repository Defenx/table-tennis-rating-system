<%--
  Created by IntelliJ IDEA.
  User: malda
  Date: 18.08.2024
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="form-auth">
    <h2 class = "auth">Авторизация</h2>
    <form action="/login" method="post">
        <div>
            <label for="email">Почта</label>
            <input type="text" id="email" name="email" required placeholder="Введите вашу почту">
        </div>
        <div>
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" required placeholder="Введите ваш пароль">
        </div>
        <div>
            <button type="submit">Войти</button>
        </div>
    </form>
</div>
</body>
</html>