function alert (content, alertType) {

    let alerts = document.getElementById("alerts");

    let a = document.createElement("DIV");
    a.innerText = content;
    a.className = "alert " + alertType + " alert-dismissible fade show mt-3";
    a.setAttribute("role", "alert");
    alerts.appendChild(a);

    a = document.createElement("BUTTON");
    a.className = "close";
    a.setAttribute("type", "button");
    a.setAttribute("data-dismiss", "alert");
    a.setAttribute("aria-label", "Close");
    alerts.lastChild.appendChild(a);

    a = document.createElement("SPAN");
    a.innerHTML = "&times;";
    a.setAttribute("aria-hidden", "true");
    alerts.lastChild.lastChild.appendChild(a);
}