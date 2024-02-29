async function register() {
    let name = document.getElementById("name").value
    let username = document.getElementById("username").value
    let email = document.getElementById("email").value
    let password = document.getElementById("password").value

    let url = "http://localhost:8080/chess/register"

    let response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            userName: username,
            email: email,
            password: password
        })
    })

    if (response.ok) {
        window.location.href = "../logIn/log-in.html"
    } else {
        let r = await response.text();
        window.alert(r)
    }

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
