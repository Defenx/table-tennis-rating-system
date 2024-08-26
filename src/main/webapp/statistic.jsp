<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Рейтинг пользователей</title>
    <style>
        table {
            width: 50%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
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
                <tr>
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
</body>
</html>