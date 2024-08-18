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
    <form action="/login" method="post">
        <div>
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" required placeholder="Введите ваше email">
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required placeholder="Введите ваш password">
        </div>
        <div>
            <button type="submit">Войти</button>
        </div>
    </form>
</div>
</body>
</html>