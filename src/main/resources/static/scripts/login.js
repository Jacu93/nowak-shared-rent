function login() {

    let email=document.getElementById("emailInput").value;
    let password=document.getElementById("passInput").value;

    let url = 'http://localhost:8080/auth/login';
    let data = {"email": email, "password": password};

    fetch(url, {
      method: 'POST',
      body: JSON.stringify(data),
      headers:{
        'Content-Type': 'application/json'
      }
    }).then(res => {
      let errorElement = document.getElementById("error");
      if(res.ok) {
        res.json().then(json => {
          console.log('Success:', JSON.stringify(json));
          window.location.href = 'main.html';
        })
      }
      else {
        res.json().then(json => {
          console.log('Internal error:', JSON.stringify(json));
          errorElement.innerHTML = "Invalid email or password!";
          errorElement.style.color = "#ff0000";
        })
      }
    })
    .catch(error => console.error('Error:', error));
}