
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
                let res = response.text();
                alert(res);
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}


function register() {
    window.location.href = "../register/register.html"
}

function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    var showPasswordCheckbox = document.getElementById("showPassword");

    if (showPasswordCheckbox.checked) {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}

  