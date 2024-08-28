<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <h2>Форма регистрации турнира</h1>
        <form action="/tournament/create" method="post">
            <!-- Тип турнира -->
            <label for="type">Тип турнира</label>
            <select id="type" name="type">
                <c:forEach var="tournamentType" items="${tournamentTypes}">
                    <option value="${tournamentType.name()}" ${tournamentType.name() == defaultTournamentType ? 'selected' : ''}>${tournamentType.getDisplayName()}</option>
                </c:forEach>
            </select>

            <!-- Дата турнира -->
            <label for="date">Дата турнира</label>
            <input type="date" id="date" name="date" required>

            <!-- Дополнения -->
            <c:if test="${not empty extensions}">
                <h3 class="centered">Дополнения:</h3>
                <c:forEach var="extension" items="${extensions}">
                    <c:choose>
                        <c:when test="${extension.getInputType() == 'CHECKBOX'}">
                            <div class="checkbox-container">
                                <input type="${extension.getInputType().getName()}" id="${extension}" name="${extension}" ${extension.getHtmlAttributesAsString()}>
                                <label for="${extension}">${extension.getDisplayName()}</label>
                            </div>
                         </c:when>
                        <c:otherwise>
                            <label for="${extension}">${extension.getDisplayName()}</label>
                            <input type="${extension.getInputType().getName()}" id="${extension}" name="${extension}" ${extension.getHtmlAttributesAsString()}>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>

            <button type="submit">Создать турнир</button>
        </form>
    </div>
</body>
</html>
