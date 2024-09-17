<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Турнир</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/runningTournament.css">
</head>
<body>
<div class="container-main">
    <h2>Турнир в процессе!</h2>
    <h2><c:out value="Раунд ${requestScope.tournament.stage}"/></h2>
    <h2><c:out value="Среднее значение рейтинга игроков: ${requestScope.averageRating}"/></h2>

    <div class="container-table">

        <c:forEach var="match" items="${requestScope.tournament.matches}">
            <div class="player-info">
                <div class="column">
                    <h2><c:out value="${match.user1.surname} ${match.user1.firstname}"/></h2>
                </div>

                <div class="column">
                    <c:choose>
                        <c:when test="${user.role == 'ADMIN'}">
                            <form action="/tournament/watch/${requestScope.tournament.id}" method="post">
                                <input type="hidden" name="matchId" value="${match.id}"/>
                                <input type="hidden" name="userId1" value="${match.user1.id}"/>
                                <input type="hidden" name="userId2" value="${match.user2.id}"/>
                                <input type="hidden" name="csrfToken" value="${csrfToken}"/>
                                <input type="hidden" name="roundToEdit" value="${roundToEdit}"
                                <c:set var="victoriesInTrainingMatches" value="0"/>
                                <c:set var="roundsInMatch" value="${requestScope.roundsInMatch}" />
                                <c:forEach var="extension" items="${requestScope.tournament.extensions}">
                                    <c:choose>
                                        <c:when test="${extension.name == 'VICTORIES_IN_TRAINING_MATCHES'}">
                                            <c:set var="victoriesInTrainingMatches" value="${extension.value}"/>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                                <c:set var="inputName1" value="score1"/>
                                <c:set var="inputName2" value="score2"/>
                                <input type="number" name="${inputName1}" class="score-input" min="0"/>
                                <button class="button" id="changeScoreButton" type="submit">Внести</button>
                                <input type="number" name="${inputName2}" class="score-input" min="0"/>

<%--                                <c:set var="roundCount" value="${fn:length(requestScope.match.rounds)}"/>--%>
                            </form>
                        </c:when>

                        <c:otherwise>
                            <h2 class="static-score"><span>0</span><span>:</span><span>0</span></h2>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="column">
                    <h2><c:out value="${match.user2.surname} ${match.user2.firstname}"/></h2>
                </div>

            </div>
        </c:forEach>
    </div>

    <div class="container-buttons">
        <c:if test="${user.role == 'ADMIN'}">
            <form action="/tournament/round/*" method="post">
                <button type="submit" class="run-button">Завершить раунд</button>
            </form>
        </c:if>
        <button type="button" class="button" onclick="redirectToLogin()">Вернуться к турнирам</button>
    </div>
</div>

<script>
    function redirectToLogin() {
        window.location.href = '/tournaments';
    }


</script>

</body>
</html>