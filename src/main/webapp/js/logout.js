/**
 * Logs the user out by sending a POST request to the server and redirects
 * the user to the login page upon successful logout.
 *
 * @async
 * @function handleLogout
 * @returns {Promise<void>} A promise that resolves when the logout operation is complete.
 * @throws {Error} Logs an error message to the console if the request fails.
 */
async function handleLogout() {
    try {
        // Sends a POST request to log out the user
        const response = await fetch('/logout', {
            method: 'POST',
            credentials: 'same-origin' // Ensures cookies are sent with the request
        });

        // Redirects the user if the server responds with a redirect
        if (response.redirected) {
            window.location.href = response.url;
        }
    } catch (error) {
        // Logs any errors that occur during the fetch request
        console.error('Error occurred during logout:', error);
    }
}

/**
 * Initializes the logout button by adding a click event listener.
 * If the button with the specified ID is not found, a warning is logged.
 *
 * @function setupLogoutButton
 */
function setupLogoutButton() {
    const logoutButton = document.getElementById('logoutButton');

    if (logoutButton) {
        // Adds a click event listener to the logout button
        logoutButton.addEventListener("click", handleLogout);
    } else {
        // Logs a warning if the button is not found
        console.warn(`Button with ID 'logoutButton' not found.`);
    }
}

// Automatically initializes the logout button when the script is loaded
setupLogoutButton();
