function checkingIntentions() {
    var isConfirmed = confirm('Are you sure?');

    if (isConfirmed) {
        window.location.href = '/login';
    }
    return isConfirmed;
}

let currentTournamentIndex =  0;
let totalTournaments = 0;

document.addEventListener('DOMContentLoaded', function() {
    totalTournaments = window.appData.totalTournaments; // Получаем значение из глобального объекта
    console.log(totalTournaments); // Проверка
    const savedIndex = localStorage.getItem('currentTournamentIndex');
    if (savedIndex !== null) {
        currentTournamentIndex = parseInt(savedIndex, 10);
    }
    showTournament(currentTournamentIndex);
    updateNavigationButtons();

    // Добавляем обработчики событий
    document.getElementById('prevTournament').addEventListener('click', showPreviousTournament);
    document.getElementById('nextTournament').addEventListener('click', showNextTournament);
});

function showTournament(index) {
    if (index >= totalTournaments){
        currentTournamentIndex = 0;
        index = 0;
        localStorage.setItem('currentTournamentIndex', index);
    }
    const tournaments = document.querySelectorAll('.container-tournament');
    tournaments.forEach((tournament, idx) => {
        if (idx === index) {
            tournament.style.display = 'block';  // Показываем нужный турнир
        } else {
            tournament.style.display = 'none';  // Скрываем все остальные
        }
    });
}

function showNextTournament() {
    if (currentTournamentIndex < totalTournaments - 1) {
        currentTournamentIndex++;
        showTournament(currentTournamentIndex);
        localStorage.setItem('currentTournamentIndex', currentTournamentIndex);
        updateNavigationButtons();
    }
}

function showPreviousTournament() {
    if (currentTournamentIndex > 0) {
        currentTournamentIndex--;
        showTournament(currentTournamentIndex);
        localStorage.setItem('currentTournamentIndex', currentTournamentIndex);
        updateNavigationButtons();
    }
}

function clearTournamentIndex() {
    localStorage.removeItem('currentTournamentIndex');
}

function updateNavigationButtons() {
    const prevButton = document.getElementById('prevTournament');
    const nextButton = document.getElementById('nextTournament');

    prevButton.disabled = currentTournamentIndex === 0;
    prevButton.classList.toggle('disabled', prevButton.disabled);

    nextButton.disabled = currentTournamentIndex === totalTournaments - 1;
    nextButton.classList.toggle('disabled', nextButton.disabled);
}