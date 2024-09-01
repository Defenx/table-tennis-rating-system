<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Рейтинг пользователей.</title>
    <link rel="stylesheet" href="static/css/statistic.css">
</head>
<body>
<div class="super-section">
    <div class="section">
        <h2>Рейтинг пользователей</h2>
        <c:if test="${not empty ratingUserList}">
            <table>
                <thead>
                <tr>
                    <th>Номер</th>
                    <th>Фамилия</th>
                    <th>Имя</th>
                    <th>Рейтинг</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${ratingUserList}" varStatus="status">
                    <tr class="${status.index == 0 ? 'first-place' : (status.index == 1 ? 'second-place' : (status.index == 2 ? 'third-place' : ''))}">
                        <td>${status.index + 1}</td>
                        <td>${user.surname}</td>
                        <td>${user.firstname}</td>
                        <td>${user.rating}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty ratingUserList}">
            <p>Нет данных для отображения.</p>
        </c:if>
    </div>
    <div class="buttons">
        <form action="/" method="get">
            <div class="buttons">
                <button type="submit">На главную</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>