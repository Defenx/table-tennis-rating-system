function setupTimers() {
    const timers = document.querySelectorAll(".timer");

    timers.forEach(timerElement => {
        const dateFromHTML = timerElement.getAttribute("data-date");
        const timeFromHTML = timerElement.getAttribute("data-time");

        const endDateTimeString = `${dateFromHTML}T${timeFromHTML}`;

        const endDate = new Date(endDateTimeString);

        const timerInterval = setInterval(() => {
            const now = new Date();

            const timeDifference = endDate - now;

            if (timeDifference <= 0) {
                clearInterval(timerInterval);
                timerElement.textContent = "Время начинать!";
                timerElement.classList.add('finished');
            } else {
                const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
                const hours = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

                timerElement.textContent = `${days}д ${hours}ч ${minutes}м ${seconds}с`;
            }
        });
    });
}
document.addEventListener("DOMContentLoaded", setupTimers);