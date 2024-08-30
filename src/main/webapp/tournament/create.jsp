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
            <input type="${ExtensionName.IS_RATING.getInputType().getName()}" id="${ExtensionName.IS_RATING}"
                   name="${ExtensionName.IS_RATING}" ${ExtensionName.IS_RATING.getHtmlAttributesAsString()}>
            <label for="${ExtensionName.IS_RATING}">${ExtensionName.IS_RATING.getDisplayName()}</label>
            <c:forEach var="ValidationError" items="${IS_RATINGValidationErrors}">
                <p class="validation-message">${ValidationError}</p>
            </c:forEach>
        </div>


            <label for="${ExtensionName.TRAINING_SETS}">${ExtensionName.TRAINING_SETS.getDisplayName()}</label>
            <input type="${ExtensionName.TRAINING_SETS.getInputType().getName()}" id="${ExtensionName.TRAINING_SETS}"
                   name="${ExtensionName.TRAINING_SETS}" ${ExtensionName.TRAINING_SETS.getHtmlAttributesAsString()}>
            <c:forEach var="ValidationError" items="${TRAINING_SETSValidationErrors}">
                <p class="validation-message">${ValidationError}</p>
            </c:forEach>


            <label for="${ExtensionName.PLAYOFF_SETS}">${ExtensionName.PLAYOFF_SETS.getDisplayName()}</label>
            <input type="${ExtensionName.PLAYOFF_SETS.getInputType().getName()}" id="${ExtensionName.PLAYOFF_SETS}"
                   name="${ExtensionName.PLAYOFF_SETS}" ${ExtensionName.PLAYOFF_SETS.getHtmlAttributesAsString()}>
            <c:forEach var="ValidationError" items="${PLAYOFF_SETSValidationErrors}">
                <p class="validation-message">${ValidationError}</p>
            </c:forEach>

        <button type="submit">Создать турнир</button>
    </form>

</div>
</body>
</html>
