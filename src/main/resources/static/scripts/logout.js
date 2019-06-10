function logout() {

    window.localStorage.removeItem("accessToken");
    console.log("Logged out...");
    window.location.href = 'login.html';
}