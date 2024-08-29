<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Турнир</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="static/css/home.css">
</head>
<body>
<div class="container-main">

    <div class="section-welcome">
        <h3>
            Добро пожаловать! <c:out value="${user.surname}"/> <c:out value="${user.firstname}"/>
            Bаш рейтинг - <c:out value="${user.rating}"/>
        </h3>

    </div>


    <div class="container-tournament">
        <c:choose>
            <c:when test="${tournament!=null}">
                <h4>Запись на турнир ${tournament.date}</h4>
                <table>
                    <c:choose>
                        <c:when test="${!isSomeoneRegisteredForTournament}">
                            <p>В данный момент участников нету</p>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <th>Фамилия</th>
                                <th>Имя</th>
                                <th>Рейтинг</th>
                                <c:if test="${user.role == 'ADMIN'}">
                                    <th></th>
                                </c:if>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach items="${tournament.participants}" var="participant">
                        <tr>
                            <td>${participant.user.surname}</td>
                            <td>${participant.user.firstname}</td>
                            <td>${participant.user.rating}</td>

                            <c:if test="${user.role == 'ADMIN'}">
                                <td>
                                    <form action="/participant/delete/${participant.id}" method="post">
                                        <input type="hidden" name="_method" value="DELETE">
                                        <button class="delete-button" type="submit"
                                                onclick="return checkingIntentions()">
                                            Удалить
                                        </button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise><h3>Турниров в данный момент нет.</h3></c:otherwise>

        </c:choose>

        <div class="container-buttons">

            <c:choose>

                <c:when test="${!isCurrentUserRegisteredForTournament}">
                    <c:if test="${tournament!=null}">
                        <form action="/home" method="post">
                            <button type="submit">
                                <c:choose>
                                    <c:when test="${!isSomeoneRegisteredForTournament}">
                                        Зарегистрируйся первым
                                    </c:when>
                                    <c:otherwise>
                                        Записаться
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </form>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <form action="/home" method="post">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" onclick="return checkingIntentions()">Отменить запись</button>
                    </form>
                </c:otherwise>
            </c:choose>


            <c:if test="${user.role == 'ADMIN'}">
                <c:choose>
                    <c:when test="${tournament==null}">
                        <form action="/tournament/create" method="get">
                                <button class="button">Создать турнир</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="/tournament/delete" method="post">
                            <input type="hidden" name="_method" value="DELETE">
                            <button class="delete-button" onclick="return checkingIntentions()">Удалить турнир</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>

    <div class="container-buttons-navigation">
        <form action="" method="post">
            <button class="button">Таблица Рейтинга</button>
        </form>
        <form action="/logout" method="post">
            <button class="button" type="submit" onclick="return checkingIntentions()">Выйти</button>
        </form>
    </div>
</div>

</body>
<script>
    function checkingIntentions() {
        var isConfirmed = confirm('Are you sure?');

        if (isConfirmed) {
            window.location.href = '/login';
        }
        return isConfirmed;
    }
</script>
</html>
