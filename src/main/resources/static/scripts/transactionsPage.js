window.onload = () => {
    if (checkToken()) {
        loadApartmentsDropdown();
    }
}

function loadApartmentsDropdown() {

    let apartmentsArray = payload.apartments;
    let apartments = document.getElementById("apartmentId");
    while (apartments.firstChild) {
        apartments.removeChild(apartments.firstChild);
    }
    for (let i = 0; i < apartmentsArray.length; i++) {
        let a = document.createElement("OPTION");
        a.innerText = apartmentsArray[i].name;
        a.setAttribute("apartmentId", apartmentsArray[i].id)
        apartments.appendChild(a);
    }
}

function newTransaction() {
    let form = document.getElementById("transactions-form");

    if (form.checkValidity() === true) {
        let title = document.getElementById("title").value;
        let value = document.getElementById("value").value;
        let transactionType = document.getElementById("transactionType");
        let apartmentId = document.getElementById("apartmentId");
        let url = '/transaction'
        let data = { 
            "apartmentId": apartmentId.options[apartmentId.selectedIndex].getAttribute("apartmentId"), 
            "title": title, 
            "type": transactionType.options[transactionType.selectedIndex].value, 
            "value": value 
        };
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
                res.json().then(json => {
                    console.log('Success:', JSON.stringify(json));
                    refreshToken();
                    alert ("Transaction added!", "alert-success");
                })
            }
            else {
                res.json().then(json => console.log('Internal error:', JSON.stringify(json)))
            }
        })
        .catch(error => console.error('Error:', error));

        setTimeout(function () {
            $('.alert').alert('close')
        }, 5000);
    }

    return false;
}