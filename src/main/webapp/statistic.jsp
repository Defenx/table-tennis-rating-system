<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <p><c:out value="${user.value.firstname}" /></p>
                <p><c:out value="${user.value.surname}" /></p>
                <p><c:out value="${user.value.rating}" /></p>
            </tr>
        </c:forEach>
</body>
</html>