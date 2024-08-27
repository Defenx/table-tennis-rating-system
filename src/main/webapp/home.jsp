<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="css/home.css">
</head>
<body>
<div class="main">
    <div class="welcome">
        <div>
            <h2>Добро пожаловать! <c:out value="${userDto.surname}"/> <c:out
                    value="${userDto.firstname}, твой рейтинг - ${userDto.rating}, ты занимаешь ${userRatingPlace} из ${usersCount}"/></h2>
        </div>
        <br><br>
        <button>Таблица Рейтинга</button>
        <button>Выход</button>
        <c:if test="${userDto.role == 'ADMIN'}">
            <button>Создать турнир</button>
        </c:if>
    </div>
    <div class="tournament">
        <c:if test="${tournament!=null}">
            <c:if test="${!isSomeoneRegisteredForTournament}">
                <form action="" method="post">
                    <button type="submit">Зарегистрируйся первым</button>
                </form>
            </c:if>
            <c:if test="${isSomeoneRegisteredForTournament}">
                <h3>Запись на турнир ${tournament.date}</h3>
                <table>
                    <tr>
                        <th>Фамилия</th>
                        <th>Имя</th>
                        <th>Рейтинг</th>
                        <c:if test="${userDto.role == 'ADMIN'}">
                            <th></th>
                        </c:if>
                    </tr>
                    <c:forEach items="${tournament.participants}" var="participant">
                        <tr>
                            <td>${participant.user.surname}</td>
                            <td>${participant.user.firstname}</td>
                            <td>${participant.user.rating}</td>
                            <c:if test="${userDto.role == 'ADMIN'}">
                                <td>
                                    <form class="removeButton" action="/participant/delete/${participant.id}" method="post">
                                        <input type="hidden" name="_method" value="DELETE">
                                        <button class="denied" type="submit">Удалить</button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>

                </table>

            </c:if>
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
        </c:if>
        <c:if test="${tournament==null}">
            <h1>Турниров в данный момент нет.</h1>
        </c:if>

    </div>
</div>
</body>
</html>
