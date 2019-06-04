var transactionTypesEnum = null;

window.onload = () => {
    if (checkToken()) {
        loadApartmentsDropdown();
        loadTransactionTypesDropdown();
        loadTransactions();
        getTransactionTypesEnum();
    }
}

function getTransactionTypesEnum() {
    fetch('/transactions/types', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(res => {
        res.json().then(json => {
            transactionTypesEnum = json;
            console.log(payload);
            console.log(transactionTypesEnum);
            }
        )
    })
    .catch(error => console.error('Error:', error));
}

function transactionTypes(enumValue) {
    switch (enumValue) {
        case 'BILL':
            return 'Bill';
        case 'COMMON_PRODUCT':
            return 'Common product';
        default:
            return 'Undefined';
    }
}

function loadApartmentsDropdown() {

    let apartmentsArray = payload.apartments;
    let apartmentsNewTran = document.getElementById("apartmentId-newTran");
    let apartmentsHistory = document.getElementById("apartmentId-history");
    while (apartmentsNewTran.firstChild || apartmentsHistory.firstChild) {
        apartmentsNewTran.removeChild(apartmentsNewTran.firstChild);
        apartmentsHistory.removeChild(apartmentsHistory.firstChild);
    }
    for (let i = 0; i < apartmentsArray.length; i++) {
        let a = document.createElement("OPTION");
        a.innerText = apartmentsArray[i].name;
        a.setAttribute("apartmentId", apartmentsArray[i].id)
        apartmentsNewTran.appendChild(a);
        apartmentsHistory.appendChild(a.cloneNode(true));
    }
}

function loadTransactionTypesDropdown() {
    let apartmentId = document.getElementById("apartmentId-newTran");
    let apartmentsArray = payload.apartments;
    let selectedApartmentId = apartmentId.options[apartmentId.selectedIndex].getAttribute("apartmentId");
    let isOwner = false;
    for (let apartment of apartmentsArray) {
        if(apartment.id == selectedApartmentId && apartment.owner) {
            isOwner = true;
        }
    }
    let transactionType = document.getElementById("transactionType");
    while (transactionType.firstChild) {
        transactionType.removeChild(transactionType.firstChild);
    }
    for (let i = 0; i < transactionTypesEnum.length; i++) {
        let a = document.createElement("OPTION");
        a.innerText = transactionTypes(transactionTypesEnum[i]);
        a.value = transactionTypesEnum[i];
        if (transactionTypesEnum[i] == 'BILL') {
            if(isOwner) {
                transactionType.appendChild(a);
            }
        } else {
            transactionType.appendChild(a);
        }
    }
}

function newTransaction() {
    let form = document.getElementById("transactions-form");

    if (form.checkValidity() === true) {
        let title = document.getElementById("title").value;
        let value = document.getElementById("value").value * 100;
        let transactionType = document.getElementById("transactionType");
        let apartmentId = document.getElementById("apartmentId-newTran");
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

function loadTransactions() {

    let apartmentId = document.getElementById("apartmentId-history");
    let currDate = new Date()

    let transactions = document.getElementById("transactions");
    while (transactions.firstChild) {
        transactions.removeChild(transactions.firstChild);
    }
    let url = '/transactions/' + (currDate.getMonth() + 1) + '_' + currDate.getFullYear() + '_' + apartmentId.options[apartmentId.selectedIndex].getAttribute("apartmentId");
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(res => {
        res.json().then(json => {
            let transactionsArray = json.transactions;
            
            for (let i = 0; i < transactionsArray.length; i++) {
                let a = document.createElement("LI");
                a.className = "list-group-item";
                transactions.appendChild(a);
                
                a = document.createElement("DIV");
                a.className = "d-flex justify-content-between align-items-center";
                transactions.lastChild.appendChild(a);

                a = document.createElement("DIV");
                a.className = "col-md-8";
                transactions.lastChild.lastChild.appendChild(a);

                a = document.createElement("H6");
                a.innerText = transactionsArray[i].title;
                a.className = "my-0";
                transactions.lastChild.lastChild.lastChild.appendChild(a);

                a = document.createElement("DIV");
                a.className = "col-md-4 text-right";
                transactions.lastChild.lastChild.appendChild(a);

                a = document.createElement("SPAN");
                a.innerText = transactionsArray[i].value/100 + ' PLN';
                transactions.lastChild.lastChild.lastChild.appendChild(a);
                
                a = document.createElement("DIV");
                a.className = "d-flex justify-content-between align-items-center";
                transactions.lastChild.appendChild(a);

                a = document.createElement("DIV");
                a.className = "col-md-4";
                transactions.lastChild.lastChild.appendChild(a);

                a = document.createElement("SMALL");
                a.innerText = timeConverter(transactionsArray[i].createdAt);
                a.className = "form-text text-muted";
                transactions.lastChild.lastChild.lastChild.appendChild(a);

                a = document.createElement("DIV");
                a.className = "col-md-8 text-right";
                transactions.lastChild.lastChild.appendChild(a);

                a = document.createElement("SMALL");
                a.innerText = transactionTypes(transactionsArray[i].type);
                a.className = "form-text text-muted";
                transactions.lastChild.lastChild.lastChild.appendChild(a);
            }
        })
    })
    .catch(error => console.error('Error:', error));
}