<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Турниры</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<div>
    <c:forEach var="tournament" items="${requestScope.launchedTournaments}">
        <fieldset>
            <form  style="display: flex;" action="/tournament/${tournament.id}" method="get">
                <h2><c:out value="${tournament.date}"/></h2>
                <button type="submit">Войти</button>
            </form>
        </fieldset>
    </c:forEach>
</div><br><br>
<div>
    <button type="button" class="button" onclick="redirectToLogin()">Вернутся на главную</button>
</div>
<script>
    function redirectToLogin() {
        window.location.href = '/home';
    }
</script>
</body>
</html>
