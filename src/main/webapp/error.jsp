<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="css/authentication.css">
</head>
<body>
<div class="form-auth">
    <h3>Ooopsss... Something wrong!</h3>
    <p style="color: red"><c:out value="${requestScope.errorMessage}"/></p>
</div>
</body>
</html>
