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
    .then(response => {
      console.log('Success:', JSON.stringify(response));
      if(response.ok) {
        document.getElementById("error").innerHTML = "gut gut";
        window.location.href = 'main.html';
      }
      else {
        document.getElementById("error").innerHTML = "Invalid email or password!";
      }
    })
    .catch(error => console.error('Error:', error));
}