<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<h2>Добро пожаловать! <c:out value="${userDto.surname}"/> <c:out value="${userDto.firstname}, твой рейтинг - ${userDto.rating}, ты занимаешь ${userRatingPlace} из ${usersCount}"/></h2>
<button>Таблица Рейтинга</button>
<button>Выход</button>
<c:if test="${userDto.role == 'ADMIN'}">
    <button>Содать турнир</button>
</c:if>
<c:if test="${tournament!=null}">
    <c:if test="${!isSomeoneRegisteredForTournament}">
        <form action="будующий сервлет для записи " method="post">
            <button type="submit">Зарегестрируйся первым</button>
        </form>
    </c:if>
    <c:if test="${isSomeoneRegisteredForTournament}">
    <table>
        <tr>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Рейтинг</th>
        </tr>
        <c:forEach items="${tournament.participants}" var="participant">
            <tr>
                <td>participant.user.surname</td>
                <td>participant.user.firstname</td>
                <td>participant.user.rating</td>
            </tr>
        </c:forEach>
        <form action="будующий сервлет для записи " method="post">
            <button type="submit">записаться</button>
        </form>
        <form action="будующий сервлет для записи " method="get">
            <input type="hidden" name="_method" value="DELETE">
            <button type="submit">отменить запись</button>
        </form>
    </table>
    </c:if>

</c:if>
<c:if test="${tournament==null}">
    <h1>Турниров в данный момент нет.</h1>
</c:if>
</body>
</html>
