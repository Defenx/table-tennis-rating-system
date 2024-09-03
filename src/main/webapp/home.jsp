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
            Добро пожаловать! <c:out value="${user.surname}"/> <c:out value="${user.firstname}"/>,
            Bаш рейтинг - <c:out value="${user.rating}"/>
        </h3>

    </div>

    <div class="container-tournament">
        <c:choose>
            <c:when test="${tournament!=null}">
                <h2>Запись на турнир ${tournament.date}</h2>
                <div class="body_timer">
                    <div class="timer">
                        <div class="timer__items">
                            <div class="timer__item timer__days">00</div>
                            <div class="timer__item timer__hours">00</div>
                            <div class="timer__item timer__minutes">00</div>
                            <div class="timer__item timer__seconds">00</div>
                        </div>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${!isSomeoneRegisteredForTournament}">
                        <p>В данный момент участников нету</p>
                    </c:when>
                    <c:otherwise>
                        <div class="container-table">
                            <table>
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ФИО</th>
                                    <th>Рейтинг</th>
                                    <c:if test="${user.role == 'ADMIN'}">
                                        <th></th>
                                    </c:if>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${tournament.participants}" var="participant" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${participant.user.surname} ${participant.user.firstname}</td>
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
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>


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
                            <button class="delete-button" onclick="return checkingIntentions()">Удалить турнир
                            </button>
                        </form>
                        <form action="/tournament/run" method="post">
                            <button class="run-button" type="submit">
                                Запустить турнир
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>

    <div class="container-buttons-navigation">
        <form action="/statistic" method="get">
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

    document.addEventListener('DOMContentLoaded', function () {
        // конечная дата
        const deadline = new Date(new Date(${tournament.date.getYear()},
                ${tournament.date.getMonthValue()-1},
                ${tournament.date.getDayOfMonth()})
            + (19*60+30) * (60 * 1000 + 999)
        );
        // id таймера
        let timerId = null;

        // склонение числительных
        function declensionNum(num, words) {
            return words[(num % 100 > 4 && num % 100 < 20) ? 2 : [2, 0, 1, 1, 1, 2][(num % 10 < 5) ? num % 10 : 5]];
        }

        // вычисляем разницу дат и устанавливаем оставшееся времени в качестве содержимого элементов
        function countdownTimer() {
            const diff = deadline - new Date();
            if (diff <= 0) {
                clearInterval(timerId);
            }
            const days = diff > 0 ? Math.floor(diff / 1000 / 60 / 60 / 24) : 0;
            const hours = diff > 0 ? Math.floor(diff / 1000 / 60 / 60) % 24 : 0;
            const minutes = diff > 0 ? Math.floor(diff / 1000 / 60) % 60 : 0;
            const seconds = diff > 0 ? Math.floor(diff / 1000) % 60 : 0;
            $days.textContent = days < 10 ? '0' + days : days;
            $hours.textContent = hours < 10 ? '0' + hours : hours;
            $minutes.textContent = minutes < 10 ? '0' + minutes : minutes;
            $seconds.textContent = seconds < 10 ? '0' + seconds : seconds;
            $days.dataset.title = declensionNum(days, ['день', 'дня', 'дней']);
            $hours.dataset.title = declensionNum(hours, ['час', 'часа', 'часов']);
            $minutes.dataset.title = declensionNum(minutes, ['минута', 'минуты', 'минут']);
            $seconds.dataset.title = declensionNum(seconds, ['секунда', 'секунды', 'секунд']);
        }

        // получаем элементы, содержащие компоненты даты
        const $days = document.querySelector('.timer__days');
        const $hours = document.querySelector('.timer__hours');
        const $minutes = document.querySelector('.timer__minutes');
        const $seconds = document.querySelector('.timer__seconds');
        // вызываем функцию countdownTimer
        countdownTimer();
        // вызываем функцию countdownTimer каждую секунду
        timerId = setInterval(countdownTimer, 1000);
    });
</script>
</html>
