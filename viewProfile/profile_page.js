let playerUOrE = localStorage.getItem("player")
const baseUrl = "http://localhost:8080/chess";
let url = baseUrl + "/playerInfo?userNameOrEmail=" + playerUOrE;

document.addEventListener("DOMContentLoaded", function () {
    getPlayerInfo();
});
async function getPlayerInfo() {
    let response = await fetch(url, null);
    if (response.ok) {
        let player = await response.json();

        let name = document.getElementById("name");
        let userName = document.getElementById("user_name");
        let email = document.getElementById("email");
        let password = document.getElementById("password");
        let pageTitle = document.getElementById("pageTitle");
        let helloTitle = document.getElementById("helloTitle")

        name.value = player.name;
        pageTitle.innerText = player.name;
        helloTitle.innerHTML = helloTitle.innerText + player.name
        userName.value = player.userName;
        email.value = player.email;
        password.value = player.password;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    let token = localStorage.getItem("token");
    if (token === null) {
        window.location.replace('../logIn/log-in.html');
    }
});

document.getElementById('homeLink').addEventListener('click', function () {
    window.location.href = '../home_page/home-page.html';
});

document.getElementById('playAgainstAiLink').addEventListener('click', function () {
    window.location.href = '../chessBoard/chessBoard.html?newGame=yes';
});

document.getElementById('learningPage').addEventListener('click', function () {
    window.location.href = '../learning_page/learning_page.html';
});
function logOut() {
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}


var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function () {
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.display === "block") {
            content.style.display = "none";
        } else {
            content.style.display = "block";
        }
    });
}

async function save() {
    let saveUrl = baseUrl + "/playerInfo/updateInfo";

    let name = document.getElementById("name").value;
    let userName = document.getElementById("user_name").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let requestBody = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            userName: userName,
            email: email,
            password: password
        })
    };

    let response = await fetch(saveUrl, requestBody);
    if(!response.ok){
        let error = await response.text();
        window.alert(error)
    }
}
