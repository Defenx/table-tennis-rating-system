<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Турнир</title>
    <link rel="stylesheet" href="css/home.css">
</head>
<body>
<div class="main">
    <div class="welcome">
        <h2>Добро пожаловать!</h2>
        <h3>
            <c:out value="${user.surname}"/>
            <c:out value="${user.firstname}, ваш рейтинг - ${user.rating}!"/>

            <c:if test="${tournament!=null}">
                <c:if test="${isCurrentUserRegisteredForTournament == true}">
                    <c:out value="Вы занимаете ${userRatingPlace}-е место из ${usersCount}"/>
                </c:if>
            </c:if>
        </h3>
    </div>
    <div class="mainButtons">
        <div class="flex-row">
            <form action="" method="post">
                <button class="leftButton">Таблица Рейтинга</button>
            </form>
            <form action="/logout" method="post">
                <button class="rightButton" type="submit">Logout</button>
            </form>
        </div>
        <div>
            <c:if test="${tournament!=null}">
                <h3>Запись на турнир ${tournament.date}</h3><br>
            </c:if>
        </div>
        <div class="flex-row">
            <c:if test="${user.role == 'ADMIN'}">
                <form action="" method="post">
                    <c:if test="${tournament==null}">
                        <button class="leftButton">Создать турнир</button>
                    </c:if>
                </form>
                <form action="/tournament/delete" method="post">
                    <input type="hidden" name="_method" value="DELETE">
                    <button class="rightButton">Удалить турнир</button>
                </form>
            </c:if>
        </div>
    </div>
    <div class="tournament">
        <c:if test="${tournament!=null}">
            <c:if test="${!isSomeoneRegisteredForTournament}">
                <form action="" method="post">
                    <button type="submit">Зарегистрируйся первым</button>
                </form>
            </c:if>
            <c:if test="${isSomeoneRegisteredForTournament}">
                <table>
                    <tr>
                        <th>Фамилия</th>
                        <th>Имя</th>
                        <th>Рейтинг</th>
                        <c:if test="${user.role == 'ADMIN'}">
                            <th></th>
                        </c:if>
                    </tr>
                    <c:forEach items="${tournament.participants}" var="participant">
                        <tr>
                            <td>${participant.user.surname}</td>
                            <td>${participant.user.firstname}</td>
                            <td>${participant.user.rating}</td>
                            <c:if test="${user.role == 'ADMIN'}">
                                <td>
                                    <form class="removeButton" action="/participant/delete/${participant.id}"
                                          method="post">
                                        <input type="hidden" name="_method" value="DELETE">
                                        <button class="deleteButton" type="submit">Удалить</button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </c:if>
        <c:if test="${tournament==null}">
            <h1>Турниров в данный момент нет.</h1>
        </c:if>
    </div>
    <div class="regOrDenied">
        <c:if test="${isCurrentUserRegisteredForTournament == false}">
            <form action="/home" method="post">
                <button class="reg" type="submit">Записаться</button>
            </form>
        </c:if>
        <c:if test="${isCurrentUserRegisteredForTournament == true}">
            <form action="/home" method="post">
                <input type="hidden" name="_method" value="DELETE">
                <button class="denied" type="submit">Отменить запись</button>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>
