<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<div id="main">
    <fieldset>
        <h3>Uuupsss... Something wrong!</h3>
        <p style="color: red"><c:out value="${requestScope.message}"/></p>
    </fieldset>
</div>
</body>
</html>
