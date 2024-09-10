let url = window.location.href;
let urlParametersIndex = url.indexOf("?");
let path;
let parameters;
let parametersArray;
let newUrl = "";

if (urlParametersIndex > -1) {

    path = url.substring(0, urlParametersIndex);
    parameters = url.substring(urlParametersIndex + 1);
    parametersArray = parameters.split("&");
    parametersArray = parametersArray.filter((el) => !el.includes("csrfToken"));

    if (parametersArray.length > 1) {
        path += "?";
    }

    newUrl += path;

    for (let parameter of parametersArray) {
        newUrl += parameter;
    }
}

window.history.pushState({}, "/", newUrl === "" ? new URL(url) : new URL(newUrl));