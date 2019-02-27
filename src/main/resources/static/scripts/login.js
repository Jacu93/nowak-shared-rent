function login() {

    var emailElement=document.getElementById("emailInput");
    var passwordElement=document.getElementById("passInput");

    var url = 'http://localhost:8080/auth/login';
    var data = {"email": emailElement.value, "password": passwordElement.value};

    fetch(url, {
      method: 'POST',
      body: JSON.stringify(data),
      headers:{
        'Content-Type': 'application/json'
      }
    }).then(res => {
      if(res.ok) {
        res.json().then(json => {
          console.log('Success:', JSON.stringify(json));
          window.location.href = 'main.html';
        })
      }
      else {
        res.json().then(json => {
          console.log('Internal error:', JSON.stringify(json));
          document.getElementById("error").innerHTML = "Invalid email or password!";
          document.getElementById("error").style.color = "#ff0000";
        })
      }
    })
    .catch(error => console.error('Error:', error));
}