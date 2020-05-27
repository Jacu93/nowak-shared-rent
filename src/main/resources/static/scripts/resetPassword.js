function resetPassword() {

    let email=document.getElementById("emailInput").value;
    let url = '/auth/password/reset';
    let data = {"email": email};

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers:{
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        let errorElement = document.getElementById("error");
        if(res.ok) {
          res.json().then(json => {
            console.log('Success!');
          })
        }
        else {
          res.json().then(json => {
            console.log('Internal error:', JSON.stringify(json));
            errorElement.innerHTML = "Invalid email! Account not found.";
            errorElement.style.color = "#ff0000";
          })
        }
      })
      .catch(error => console.error('Error:', error));

      setTimeout(function () {
        $('.alert').alert('close')
      }, 5000);
  
      return false;
}