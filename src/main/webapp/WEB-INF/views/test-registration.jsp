<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <div class="text-center mt-5 b-1">
        <h2 class="h2 my-1 font-weight-normal">Test registration</h2>
    </div>
    <div class="col-8 col-lg-4 offset-2 offset-lg-4">
        <form class="p-4 mt-5 border" action="/test-registration" method="post">
            <div class="form-label-group">
                <label for="name" class="text-muted">Name</label>
                <input type="text" id="name" name="name" class="form-control my-2">
                <c:if test="${not empty nameValidationErrors}">
                    <c:forEach var="nameValidationError" items="${nameValidationErrors}">
                        <div class="form-text text-danger">${nameValidationError}</div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-label-group">
                <label for="email" class="text-muted">Email</label>
                <input type="email" id="email" name="email" class="form-control my-2">
                <c:forEach var="emailValidationError" items="${emailValidationErrors}">
                    <div class="form-text text-danger">${emailValidationError}</div>
                </c:forEach>
            </div>
            <div class="form-label-group">
                <label for="age" class="text-muted">Age</label>
                <input type="number" id="age" name="age" class="form-control my-2">
                <c:forEach var="ageValidationError" items="${ageValidationErrors}">
                    <div class="form-text text-danger">${ageValidationError}</div>
                </c:forEach>
            </div>
            <div class="form-label-group">
                <label for="password" class="text-muted">Password</label>
                <input type="password" id="password" name="password" class="form-control my-2">
                <c:forEach var="passwordValidationError" items="${passwordValidationErrors}">
                    <div class="form-text text-danger">${passwordValidationError}</div>
                </c:forEach>
            </div>
            <button class="btn btn-success btn-block mt-3 w-100" type="submit">Register</button>
        </form>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</html>