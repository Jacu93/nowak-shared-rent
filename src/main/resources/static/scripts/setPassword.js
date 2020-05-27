function setPassword() {

    let password = document.getElementById("passInput").value;
    let repassword = document.getElementById("repassInput").value;
    let url = '/auth/password/set';
    const urlParams = new URLSearchParams(window.location.search);
    let data = { "password": password, "resetPasswordKey": urlParams.get('key')};

    if (password !== repassword) {

        document.getElementById("error").innerHTML = "Not the same passwords";
        document.getElementById("error").style.color = "#ff0000";
        return false;
    } else if (!(password.length > 3)) {

        document.getElementById("error").innerHTML = "Too short password";
        document.getElementById("error").style.color = "#ff0000";
        return false;
    } else {

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

                    window.location.href = 'index.html';
                })
            } else {

                res.json().then(json => {
                    
                    console.log('Internal error:', JSON.stringify(json));
                    errorElement.innerHTML = "Invalid reset link. Please try again";
                    errorElement.style.color = "#ff0000";
                })
            }
        })
        .catch(error => console.error('Error:', error));
    
        return false;

    }
}