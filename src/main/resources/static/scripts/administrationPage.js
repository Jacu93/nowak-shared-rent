window.onload = () => {
    checkToken();
};

function newApartment() {
    let address = document.getElementById("address").value;
    let city = document.getElementById("city").value;

    let url = './apartment'
    let data = { "address": address, "city": city };
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', window.localStorage.getItem("accessToken"));

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: headers
    })
        .then(res => {
            if (res.ok) {
                console.log('Apartment created');
            }
        })
        .catch(error => console.error('Error:', error));
}