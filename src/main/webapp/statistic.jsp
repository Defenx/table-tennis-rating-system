<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Рейтинг пользователей</title>
</head>
<body>
<h2>Рейтинг пользователей</h2>
        <c:forEach var="user" items="${ratingUserList}" >
            <tr>
                <p><c:out value="${user.firstname}" /></p>
                <p><c:out value="${user.surname}" /></p>
                <p><c:out value="${user.rating}" /></p>
            </tr>
        </c:forEach>
</body>
</html>