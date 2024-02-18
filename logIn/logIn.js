
function logIn() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const springBootPort = 8080; 
    const springBootURL = `http://localhost:${springBootPort}/chess/login`;

    const requestData = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email: username, password: password })

    };

    fetch(springBootURL, requestData)
        .then(response => {
            if (response.ok) {
                let res = response.text();
                localStorage.setItem("token", res)
                localStorage.setItem("player", username)
                window.location.href = '../home_page/home-page.html'
            } else {
                alert("Login failed. Please check your credentials.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}


function register() {
    window.location.href = "../register/register.html"
}
