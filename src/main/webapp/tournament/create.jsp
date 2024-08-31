<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="enums.ExtensionName"%>
<%@page import="enums.TournamentType" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Форма турнира</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tournament-create.css">
</head>
<body>
<div class="form-container">
    <h2>Форма регистрации турнира</h2>
    <form action="/tournament/create" method="post">
        <!-- Тип турнира -->
        <label for="type">Тип турнира</label>
        <select id="type" name="type">
            <c:forEach var="tournamentType" items="${TournamentType.values()}">
                <option value="${tournamentType.name()}" ${tournamentType.name() == TournamentType.SINGLE_PLAYER ? 'selected' : ''}>${tournamentType.getDisplayName()}</option>
            </c:forEach>
        </select>

        <!-- Дата турнира -->
        <label for="date">Дата турнира</label>
        <input type="date" id="date" name="date" required>

        <div class="checkbox-container">
            <input type="checkbox" id="isRating"
                   name="isRating" checked="">
            <label for="isRating">Рейтинговый турнир</label>
            <c:forEach var="ValidationError" items="${isRatingValidationErrors}">
                <p class="validation-message">${ValidationError}</p>
            </c:forEach>
        </div>


            <label for="trainingSets">Кол-во побед в тренировочных матчах</label>
            <input type="number" id="trainingSets"
                   name="trainingSets" min="1" max="5">
            <c:forEach var="ValidationError" items="${trainingSetsValidationErrors}">
                <p class="validation-message">${ValidationError}</p>
            </c:forEach>


            <label for="playoffSets">Кол-во побед в плейофф матчах</label>
            <input type="number" id="playoffSets"
                   name="playoffSets" min="1" max="5">
            <c:forEach var="ValidationError" items="${playoffSetsValidationErrors}">
                <p class="validation-message">${ValidationError}</p>
            </c:forEach>

        <button type="submit">Создать турнир</button>
    </form>

</div>
</body>
</html>
