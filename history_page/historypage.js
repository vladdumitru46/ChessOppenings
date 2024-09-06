async function gameHisotry() {
    let token = localStorage.getItem("token")
    let url = "http://localhost:8080/chess/game/history?token=" + token;
    let response = await fetch(url, null);
    if (response.ok) {
        let list = document.getElementById("gameList");
        let gameList = await response.json();
        for (var i in gameList) {
            let game = gameList[i];
            let turn = game.whitesTurn ? "white" : "black";
            let playerColour = game.playerColour;
            let string = "move: " + game.moveNumber + ", turn: " + turn + ", status: " + game.gameStatus + ", player colour: " + playerColour;
            let newItem = document.createElement("li");

            newItem.id = game.id;
            newItem.playerColour = game.playerColour;
            newItem.className = "historypage-li list-item";

            let spanElement = document.createElement("span");

            spanElement.textContent = string;

            newItem.appendChild(spanElement);
            newItem.addEventListener("click", function () {
                localStorage.setItem("boardId", newItem.id)
                localStorage.setItem("playerColour", newItem.playerColour);
                if (playerColour == "white") {
                    window.location.href = "../chessBoard/chessBoard.html";
                } else {
                    window.location.href = "../chessBoard/chessBoardBlack.html";
                }
            });
            list.appendChild(newItem);
        }
    } else {
        let res = await response.text();
        // window.alert(res)
        let list = document.getElementById("gameList");
        let newItem = document.createElement("li");
        let spanElement = document.createElement("span");
        newItem.className = "historypage-li list-item";
        spanElement.textContent = res;
        newItem.appendChild(spanElement)
        list.appendChild(newItem);
    }
}

gameHisotry();

document.addEventListener('DOMContentLoaded', function () {
    let token = localStorage.getItem("token");
    if (token === null) {

        window.location.replace('../logIn/log-in.html');
    }
});

document.getElementById('homeLink').addEventListener('click', function () {
    window.location.href = '../home_page/home-page.html';
});

document.getElementById('learningPageLink').addEventListener('click', function () {
    window.location.href = '../learning_page/learning_page.html';
});
document.getElementById('playAgainstAiLink').addEventListener('click', function () {
    window.location.href = '../choseColour/chose-black-or-white.html';
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