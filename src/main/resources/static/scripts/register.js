function register() {

    var emailElement=document.getElementById("emailInput");
    var nameElement=document.getElementById("nameInput");
    var passwordElement=document.getElementById("passInput");
    var repasswordElement=document.getElementById("repassInput");

    var url = 'http://localhost:8080/auth/signup';
    var data = {"email": emailElement.value, "name": nameElement.value, "password": passwordElement.value};

    if (passwordElement.value !== repasswordElement.value){
        document.getElementById("error").innerHTML = "Not the same passwords";
        return;
    }
    if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(emailElement.value))){
        document.getElementById("error").innerHTML = "Wrong email address";
        return;
    }
    if (!(passwordElement.value.length>3)){
        document.getElementById("error").innerHTML = "Too short password";
        return;
    }

    fetch(url, {
      method: 'POST',
      body: JSON.stringify(data),
      headers:{
        'Content-Type': 'application/json'
      }
    }).then(res => res.json())
    .then(response => console.log('Success:', JSON.stringify(response)))
    .catch(error => console.error('Error:', error));

    document.getElementById("error").innerHTML = "Account registered successfully!";
    document.getElementById("error").style.color = "#009933";
}