async function register() {
    let name = document.getElementById("name").value
    let username = document.getElementById("username").value
    let email = document.getElementById("email").value
    let password = document.getElementById("password").value
    const baseUrl = "http://localhost:8080/chess"
    let url = baseUrl + "/register"
    let error = verifyCredentials(name, username, email, password);
    if (error == "") {
        console.log(url);
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
            window.alert("Registration is complete, please check your email to confirm your account!");
            window.location.href = "../logIn/log-in.html"
        } else {
            let r = await response.text();
            window.alert(r)
        }
    } else {
        window.alert(error)
    }

}

function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    if (passwordInput.type === "password") {
      passwordInput.type = "text";
    } else {
      passwordInput.type = "password";
    }
  }


function verifyCredentials(name, username, email, password) {
    let error = "";
    if (!name || name.trim() === "") {
        error += "Name cannot be empty." + "\n";
    }

    if (!username || username.trim() === "") {
        error += "Username cannot be empty." + "\n";
    }

    const emailPattern = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,6}$/;
    if (!email || !emailPattern.test(email)) {
        error += "Email is not valid." + "\n";
    }

    const passwordPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!password || !passwordPattern.test(password)) {
        error += "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a number, and a special character. " + "\n";
    }

    return error;
}

