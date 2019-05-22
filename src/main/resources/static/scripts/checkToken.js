var payload = null;

function decodeToken(token) {
    
    let encodedPayload = token.split(' ')[1];
    encodedPayload = encodedPayload.split('.')[1];
    let decodedPayload = atob(encodedPayload);
    // let decodedPayloadUTF = Base64Decode(encodedPayload);
    // console.log(JSON.parse(decodedPayloadUTF));
    payload = JSON.parse(decodedPayload);
}

function checkToken() {

    let token = window.localStorage.getItem("accessToken");
    if (token == null || token.length < 1) {
        window.location.href = "login.html";
        return false;
    }
    else {
        decodeToken(token);
        if (payload.exp > Date.now() / 1000) {
            return true;
        }
        else {
            window.location.href = "login.html";
            return false;
        }
    }
}

function Base64Decode(str, encoding = 'utf-8') {
    var bytes = base64js.toByteArray(str);
    return new (typeof TextDecoder === "undefined" ? TextDecoderLite : TextDecoder)(encoding).decode(bytes);
}