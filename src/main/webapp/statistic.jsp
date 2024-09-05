<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Рейтинг пользователей</title>
    <link rel="stylesheet" href="static/css/statistic.css">
</head>
<body>
<div class="container-main">
    <h3>Рейтинг пользователей</h3>
    <c:choose>
        <c:when test="${not empty ratingUserList}">
            <div class="container-table">
                <table>
                    <thead>
                    <tr>
                        <th>№</th>
                        <th>ФИО</th>
                        <th>Рейтинг</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${ratingUserList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${user.surname} ${user.firstname}</td>
                            <td>${user.rating}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <p>Нет данных для отображения.</p>
        </c:otherwise>
    </c:choose>

    <div class="buttons">
        <form action="/home" method="get">
            <div class="buttons">
                <button type="submit">На главную</button>
            </div>
        </form>
    </div>

</div>
<script type="text/javascript">
    const findAllForms = document.body.querySelectorAll("form");

    findAllForms.forEach((el) => {
        let input = el.appendChild(document.createElement("input"));
        input.setAttribute("name", "csrfToken");
        input.setAttribute("value", "<c:out value="${csrfToken}" />");
        input.setAttribute("type", "hidden");
    });
</script>
</body>
</html>