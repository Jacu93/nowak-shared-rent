var payload = null;

function decodeToken(token) {
    let encodedPayload = token.split(' ')[1];
    encodedPayload = encodedPayload.split('.')[1];
    let decodedPayload = atob(encodedPayload);
    payload = JSON.parse(decodedPayload);
}

function checkToken() {

    let token = window.localStorage.getItem("accessToken");

    if (token == null) {
        window.location.href = "./login.html"
    }
    else {
        decodeToken(token);

        if (payload.exp < Date.now() / 1000) {
            window.location.href = "./login.html"
        }
    }
}