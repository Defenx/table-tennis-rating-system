<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Проходящие турниры</title>
    <link rel="stylesheet" href="static/css/tournaments.css">
</head>
<body>
<div class="container-main">
    <h3>Активные турниры</h3>
    <c:if test="${not empty tournamentsList}">
        <div class="container-table">
            <table>
                <thead>
                <tr>
                    <th>Дата начала турнира</th>
                    <th>Количество участников</th>
                    <th>Тип турнира</th>
                    <th>Действие</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tournament" items="${tournamentsList}">
                    <tr>
                        <td>${tournament[1]}</td>
                        <td>${tournament[3]}</td>
                        <td>${tournament[2]}</td>
                        <td>
                            <form action="/tournament/watch/${tournament[0]}" method="get">
                                <button type="submit">Следить за турниром</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <c:if test="${empty tournamentsList}">
        <p>Сейчас не проходят турниры!</p>
    </c:if>
    <div class="buttons">
        <form action="/home" method="get">
            <div class="buttons">
                <button type="submit">На главную</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>