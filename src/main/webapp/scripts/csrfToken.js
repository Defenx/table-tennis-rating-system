let builder = String();
let url = window.location.href;
let urlParametersIndex = url.indexOf("?");
let path = url.substring(0, urlParametersIndex);
let parameters = url.substring(urlParametersIndex + 1);
let parametersArray = parameters.split("&");

parametersArray = parametersArray.filter((el) => !el.includes("csrfToken"));

builder += path;

if (parametersArray.length !== 0) {
    builder += "?";

    for (let parameter of parametersArray) {
        builder += parameter;
    }
}


window.history.pushState({}, "/", new URL(builder));