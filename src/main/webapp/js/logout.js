document.getElementById('logoutButton')
    .addEventListener("click", () => {
        fetch('/logout', {
            method: 'POST',
            credentials: 'same-origin'
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                }
            })
    });
