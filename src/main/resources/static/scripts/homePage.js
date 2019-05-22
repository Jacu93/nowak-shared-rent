var selectedApartmentId = null;

window.onload = () => {

    if (checkToken()) {
        loadInvitations();
        loadApartments();
        selectApartment(0);
    }
}

function loadInvitations() {

    let invitations = document.getElementById("invitations");
    let url = '/invitation';
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', window.localStorage.getItem("accessToken"));

    fetch(url, {
        method: 'GET',
        headers: headers
    })
    .then(res => {
        res.json().then(json => {
            let invitationsArray = json;
            
            for (let i = 0; i < invitationsArray.length; i++) {
                let a = document.createElement("LI");
                a.className = "list-group-item d-flex justify-content-between";
                invitations.appendChild(a);
        
                a = document.createElement("DIV")
                invitations.lastChild.appendChild(a);

                a = document.createElement("H6")
                a.innerText = invitationsArray[i].apartmentName;
                a.className = "my-0";
                invitations.lastChild.lastChild.appendChild(a);

                a = document.createElement("SMALL")
                a.innerText = invitationsArray[i].sender + " is the administrator";
                a.className = "text-muted";
                invitations.lastChild.lastChild.appendChild(a);
        
                a = document.createElement("DIV")
                invitations.lastChild.appendChild(a);

                a = document.createElement("BUTTON")
                a.innerText = "Accept";
                a.className = "btn btn-success";
                a.setAttribute("onclick", "resolveInvitation(\'" + invitationsArray[i].id + "\', \'accept\')");
                invitations.lastChild.lastChild.appendChild(a);

                a = document.createElement("BUTTON")
                a.innerText = "Decline";
                a.className = "btn btn btn-danger";
                a.setAttribute("onclick", "resolveInvitation(\'" + invitationsArray[i].id + "\', \'reject\')");
                invitations.lastChild.lastChild.appendChild(a);
            }
        })
    })
    .catch(error => console.error('Error:', error));
}

function loadApartments() {

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
}

function loadRoommates(ApartmentId) {

    let roommates = document.getElementById("roommates");
    while (roommates.firstChild) {
        roommates.removeChild(roommates.firstChild);
    }
    let url = '/apartment/' + ApartmentId;
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

            if (json.admin != payload.email) {
                document.getElementById("invite-form").setAttribute("disabled","");
            }
        })
    })
    .catch(error => console.error('Error:', error));
}

function selectApartment(id) {

    document.getElementById("invite-form").removeAttribute("disabled");
    let apartments = document.getElementById("apartments").children;
    selectedApartmentId = apartments[id].getAttribute("apartmentId");
    document.getElementById("apartmentName").innerText = "Roommates of " + apartments[id].innerText;
    loadRoommates(selectedApartmentId);
}

function inviteTenant() {

    let email = document.getElementById("email").value;
    let url = '/invite'
    let data = { "receiver": email, "apartmentId": selectedApartmentId };
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
            res.json().then(json => console.log('Success:', JSON.stringify(json)))
            alert ("User with email " + email + " successfuly invited!", "alert-success");
        }
        else {
            res.json().then(json => {
                console.log(JSON.stringify(json));
                alert (json.message, "alert-danger");
            })
        }
    })
    .catch(error => console.error('Error:', error));

    setTimeout(function () {
        $('.alert').alert('close')
    }, 5000);

    return false;
}

function resolveInvitation(id, action) {

    let invitations = document.getElementById("invitations").children;
    let url = '/invitation/' + id + '/' + action;
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', window.localStorage.getItem("accessToken"));

    fetch(url, {
        method: 'POST',
        headers: headers
    })
    .then(res => {
        if (res.ok) {
            res.json().then(json => {
                console.log('Success:', JSON.stringify(json));
                refreshToken();
                location.reload(true);
            })
        }
        else {
            res.json().then(json => console.log('Internal error:', JSON.stringify(json)))
        }
    })
    .catch(error => console.error('Error:', error));
}