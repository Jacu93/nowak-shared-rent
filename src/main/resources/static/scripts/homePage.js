var selectedApartmentId = null;

window.onload = () => {
    checkToken();

    let apartmentsArray = payload.apartments;
    let apartments = document.getElementById("apartments");

    while (apartments.firstChild) {
        apartments.removeChild(apartments.firstChild);
    }
    for (let i = 0; i < apartmentsArray.length; i++) {
        let a = document.createElement("DIV");
        a.innerText = apartmentsArray[i].name;
        a.className = "list-group-item list-group-item-action";
        a.setAttribute("data-toggle", "list");

        a.setAttribute("onclick", "selectApartment(" + i + ")");
        a.setAttribute("apartmentId", apartmentsArray[i].id)

        apartments.appendChild(a);
    }
    apartments.firstChild.className += " active";
    selectApartment(0);
}

function selectApartment(id) {
    let apartments = document.getElementById("apartments").children;
    selectedApartmentId = apartments[id].getAttribute("apartmentId");

    let roommates = document.getElementById("roommates");
    while (roommates.firstChild) {
        roommates.removeChild(roommates.firstChild);
    }

    let url = './apartment?id=' + selectedApartmentId;
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            res.json().then(json => {
                let tenantsArray = json.tenants;
                
                for (let i = 0; i < tenantsArray.length; i++) {
                    let a = document.createElement("LI");
                    a.className = "list-group-item d-flex justify-content-between lh-condensed";
                    roommates.appendChild(a);
            
                    a = document.createElement("H6")
                    a.innerText = tenantsArray[i].name;
                    a.className = "my-0";
                    roommates.lastChild.appendChild(a);
            
                    a = document.createElement("SPAN")
                    a.innerText = "100 PLN";
                    a.className = "text-muted";
                    roommates.lastChild.appendChild(a);
                }
            })
        })
        .catch(error => console.error('Error:', error));
}

function inviteTenant() {
    let email = document.getElementById("email").value;
    let url = './invite'
    let data = { "receiver": email, "apartmentId": selectedApartmentId };
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', window.localStorage.getItem("accessToken"));

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: headers
    })
        .then(res => console.log('Invitation sent'))
        .catch(error => console.error('Error:', error));
}