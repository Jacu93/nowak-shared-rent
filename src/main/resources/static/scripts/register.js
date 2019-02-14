function register() {

    var emailElement=document.getElementById("emailInput");
    var nameElement=document.getElementById("nameInput");
    var passwordElement=document.getElementById("passInput");
    //var repasswordElement=document.getElementById("repassInput");

    var url = 'http://localhost:8080/auth/signup';
    var data = {"email": emailElement, "name": nameElement, "password": passwordElement};

    fetch(url, {
      method: 'POST',
      body: JSON.stringify(data),
      headers:{
        'Content-Type': 'application/json'
      }
    }).then(res => res.json())
    .then(response => console.log('Success:', JSON.stringify(response)))
    .catch(error => console.error('Error:', error));
}