<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Турнир</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="static/css/home.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container-main">

    <div class="section-welcome">
        <h3>
            Добро пожаловать! <c:out value="${user.surname}"/> <c:out value="${user.firstname}"/>,
            Ваш рейтинг - <c:out value="${user.rating}"/>
        </h3>
    </div>
    <c:choose>
        <c:when test="${fn:length(tournamentsWithStatusNew) != 0}">
            <div class="tournament-navigation">
                <c:set var="totalTournaments" value="${fn:length(tournamentsWithStatusNew)}"/>
                <button id="prevTournament" class="arrow-button prev-button"></button>
                <label for="prevTournament"> Предыдущий</label>
                <h2 class="page-title">Запись на турнир</h2>
                <label for="nextTournament"> Следующий</label>
                <button id="nextTournament" class="arrow-button next-button"></button>
            </div>
        </c:when>
        <c:otherwise>
            <h2>На данный момент турниров нет</h2>
        </c:otherwise>
    </c:choose>
    <c:forEach var="tournament" items="${tournamentsWithStatusNew}">
        <div class="container-tournament" id="tournament-${status.index}">
            <c:choose>
                <c:when test="${fn:length(tournamentsWithStatusNew) != 0}">
                    <h2>Дата проведения: ${tournament.date}</h2>
                    <div class="timer" data-date="${tournament.date}" data-time="19:30:00"
                         id="timer-${tournament.id}"></div>
                    <h3>Зарегистрировано: ${fn:length(tournament.participants)}</h3>

                    <c:choose>
                        <c:when test="${fn:length(tournament.participants) == 0}">
                            <p>В данный момент участников нет</p>
                            <form action="/tournament/participation/${tournament.id}" method="post">
                                <button type="submit">Зарегистрируйся первым</button>
                                <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}" />">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <div class="container-table">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>№</th>
                                        <th>ФИО</th>
                                        <th>Рейтинг</th>
                                        <c:if test="${user.role == 'ADMIN'}">
                                            <th></th>
                                        </c:if>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${tournament.participants}" var="participant" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td>${participant.user.surname} ${participant.user.firstname}</td>
                                            <td>${participant.user.rating}</td>

                                            <c:if test="${user.role == 'ADMIN'}">
                                                <td>
                                                    <form action="/participant/delete/${participant.id}" method="post">
                                                        <input type="hidden" name="_method" value="DELETE">
                                                        <button class="delete-button" type="submit"
                                                                onclick="return checkingIntentions()">
                                                            Удалить
                                                        </button>
                                                        <input type="hidden" name="csrfToken"
                                                               value="<c:out value="${csrfToken}" />">
                                                    </form>
                                                </td>
                                            </c:if>
                                        </tr>

                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:otherwise><h3>Турниров в данный момент нет.</h3></c:otherwise>

            </c:choose>

            <div class="container-buttons">

                <c:if test="${fn:length(tournament.participants) != 0}">
                    <c:set var="isParticipate" value="isCurrentUserRegisteredForTournament${tournament.id}"/>
                    <c:choose>

                        <c:when test="${!requestScope[isParticipate]}">
                            <form action="/tournament/participation/${tournament.id}" method="post">
                                <button type="submit">Записаться</button>
                                <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}" />">
                            </form>

                        </c:when>
                        <c:otherwise>
                            <form action="/tournament/participation/${tournament.id}" method="post">
                                <input type="hidden" name="_method" value="DELETE">
                                <button type="submit" onclick="return checkingIntentions()">Отменить запись</button>
                                <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}" />">
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${user.role == 'ADMIN'}">
                    <form action="/tournament/delete/${tournament.id}" method="post">
                        <input type="hidden" name="_method" value="DELETE">
                        <button class="delete-button" onclick="return checkingIntentions() && clearTournamentIndex();">
                            Удалить турнир
                        </button>
                        <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}" />">
                    </form>
                    <form action="/tournament/run/${tournament.id}" method="post">
                        <button class="run-button" type="submit">
                            Запустить турнир
                        </button>
                        <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}" />">
                    </form>
                </c:if>
            </div>
        </div>
    </c:forEach>

    <c:if test="${user.role == 'ADMIN'}">
        <form action="/tournament/create" method="get">
            <button class="button">Создать турнир</button>
        </form>
    </c:if>

    <div class="container-buttons-navigation">
        <form action="/statistic" method="get">
            <button class="button">Таблица Рейтинга</button>
        </form>
        <form action="/Здесь_могла_быть_ваша_реклама" method="get">
            <button class="button">Проходящие Турниры</button>
        </form>
        <form action="/logout" method="post">
            <button class="button" type="submit" onclick="return checkingIntentions() && clearTournamentIndex();">
                Выйти
            </button>
            <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}" />">
        </form>
    </div>
</div>

</body>
<script src="scripts/timer.js"></script>
<script src="scripts/home.js"></script>
<script>
    window.appData = {
        totalTournaments: ${totalTournaments}
    };
</script>

</html>
