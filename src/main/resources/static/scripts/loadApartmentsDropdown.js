function loadApartmentsDropdown(elementId) {

    let apartmentsArray = payload.apartments;
    let apartmentsDropdown = document.getElementById(elementId);
    while (apartmentsDropdown.firstChild) {
        apartmentsDropdown.removeChild(apartmentsDropdown.firstChild);
    }
    for (let i = 0; i < apartmentsArray.length; i++) {
        let a = document.createElement("OPTION");
        a.innerText = apartmentsArray[i].name;
        a.setAttribute("apartmentId", apartmentsArray[i].id)
        apartmentsDropdown.appendChild(a);
    }
}