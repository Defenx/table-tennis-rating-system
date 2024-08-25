<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.08.2024
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="enums.TournamentType" %>

<html>
<head>
    <title>Создание турнира</title>
</head>
<body>
<h1>Создание турнира</h1>
<form>
    <label for="Тип">Тип</label>
    <input type="text" list="tournamentType" id="Тип" name="Тип">
    <datalist id="tournamentType">
        <option value="<%=TournamentType.SINGLE_PLAYER.toString()%>">
        <option value="Firefox">
    </datalist>
</form>
</body>
</html>
