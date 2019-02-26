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
    }).then(res => res.json())
    .then(response => console.log('Success:', JSON.stringify(response)))
    .catch(error => console.error('Error:', error));

    window.location.href = 'main.html';
}