const baseUrl = "http://localhost:8080/chess";

async function logIn() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const springBootPort = 8080;
    const springBootURL = `${baseUrl}/login`;

    const requestData = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email: username, password: password })

    };

    let response = await fetch(springBootURL, requestData)
    if (response.ok) {
        let res = await response.text();
        localStorage.setItem("token", res)
        localStorage.setItem("player", username)
        window.location.href = '../home_page/home-page.html'
    } else {
        let res = await response.text();
        alert(res);
    }
}


function register() {
    window.location.href = "../register/register.html"
}

function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}

