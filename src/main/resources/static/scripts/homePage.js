window.onload = () => {
    let url = 'http://www.mocky.io/v2/5c9f64573300006d00a87cda';
    
    fetch(url, {
        method: 'GET'
      })
      .then(res => res.json())
      .then(json => {
          let apartments = document.getElementById("apartments");
          while (apartments.firstChild) {
              apartments.removeChild(apartments.firstChild);
          }
          for (let apartmentData of json) {
            var a = document.createElement("A");
            a.innerText = apartmentData.name;
            a.className = "list-group-item list-group-item-action";
            a.setAttribute ("data-toggle", "list");
            apartments.appendChild(a);
          }
          apartments.firstChild.className += " active";
      });
     
  };