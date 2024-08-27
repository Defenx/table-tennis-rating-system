<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.08.2024
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>


<html>
<head>
    <title>Создание турнира</title>
</head>
<body>
<div align="center">
    <h1>Создание турнира</h1>
</div>

<form action="/tournament/create" method="post">
    <div align="center">
        <label for="type">Тип турнира:</label>
        <select id="type" name="type">
            <c:forEach var="tournamentType" items="${tournamentTypes}">
                <option value="${tournamentType}">${tournamentType}</option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div align="center">
        <label for="date">Дата турнира</label>
        <input type="date" id="date" name="date">
    </div>
    <br>

    <c:forEach var="extensionName" items="${extensionNames}">
        <div align="center">
            <label for="${extensionName}">${extensionName.getDescription()}</label> <br>
            <input type="number" id="${extensionName}" name=${extensionName}>
        </div>
    </c:forEach>




    <br>
    <div align="center">
        <label for="numberOfSetInPlayOff">Количество сетов в плейофф</label> <br>
        <input type="number" id="numberOfSetInPlayOff" name="numberOfSetInPlayOff">
    </div>
    <br>
    <div align="center">
        <label>Идет в зачет рейтинга:</label><br>
        <input type="radio" name="isRating" value="true" checked="checked">Да
        <input type="radio" name="isRating" value="false">Нет


    </div>
    <br>
    <div align="center">
        <label for="numberOfPlayers">Количество участников</label> <br>
        <input type="number" id="numberOfPlayers" name="numberOfPlayers">
    </div>
    <br>
    <br>
    <br>
    <div align="center">
        <button type="submit">Создать</button>
        <a href="/tournament/create"></a>
    </div>
</form>
</body>
</html>
