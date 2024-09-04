<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Турнир</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<div>
    <h2>Турнир в процессе!</h2>
    <h2><c:out value="Раунд ${stage}"/></h2>
    <h2><c:out value="Average rating: ${requestScope.avg}"/></h2>
    <c:forEach var="match" items="${requestScope.matches}">
        <div style="display: flex">
            <h2 style="color: blue"><c:out
                    value="${match.user1.surname} ${match.user1.firstname}"/></h2>
            <h3></h3>
            <c:choose>
            <c:when test="${user.role == 'ADMIN'}">
            <form style="display: flex" action="/tournament/round" method="post">
                <input style="color: black; width: 70px" type="number">
                <h3> : </h3>
                <button>Внести</button>

                <h3> : </h3>
                <input style="color: black; width: 70px" type="number">
                </c:when>
                <c:otherwise>
                    <h2 value="">0</h2>
                    <h3> : </h3>
                    <h2 value="">0</h2>
                </c:otherwise>
                </c:choose>
            </form>
            <h3></h3>
            <h2 style="color: blue"><c:out
                    value="${match.user2.surname} ${match.user2.firstname}"/></h2>
        </div>
    </c:forEach>
</div>
<div>
    <c:choose>
        <c:when test="${user.role == 'ADMIN'}">
        <form  action="/tournament/round/*" method="post">
            <button type="button" class="button">Завершить раунд</button>
        </form>
        </c:when>
        <c:otherwise>
            <button type="button" class="button" onclick="redirectToLogin()">Вернутся на главную</button>
        </c:otherwise>
    </c:choose>
</div>
<script>
    function redirectToLogin() {
        window.location.href = '/home';
    }
</script>
</body>
</html>
