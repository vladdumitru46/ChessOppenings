var black = document.getElementById('black');
var white = document.getElementById('white');

black.addEventListener('click', function () {
    localStorage.setItem("playerColour", "black")
    window.location.href = "../chessBoard/chessBoardBlack.html?newGame=yes";
});

white.addEventListener('click', function () {
    localStorage.setItem("playerColour", "white")
    window.location.href = "../chessBoard/chessBoard.html?newGame=yes";
});

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
    window.location.href = '../learning_page/learning_page.html';
});

function logOut() {
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}

function viewProfile() {
    window.location.replace('../viewProfile/profile-page.html');
}

var coll = document.getElementsByClassName("collapsible");
var i;
console.log(coll)
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