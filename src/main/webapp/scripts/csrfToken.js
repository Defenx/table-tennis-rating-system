const url = new URL(window.location.href);
const params = new URLSearchParams(url.search);
const newUrl = url.href.replace(params.get("csrfToken"),"******")
window.history.pushState({},"/", new URL(newUrl));