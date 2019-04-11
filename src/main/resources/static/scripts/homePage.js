window.onload = () => {
    checkToken();
    let apartmentsArray = payload.apartments;
    let apartments = document.getElementById("apartments");
    while (apartments.firstChild) {
        apartments.removeChild(apartments.firstChild);
    }
    for (let apartmentData of apartmentsArray) {
        var a = document.createElement("A");
        a.innerText = apartmentData.name;
        a.className = "list-group-item list-group-item-action";
        a.setAttribute("data-toggle", "list");
        apartments.appendChild(a);
    }
    apartments.firstChild.className += " active";
}