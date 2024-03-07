async function gameHisotry() {
    let player = localStorage.getItem("player")
    let url = "http://localhost:8080/chess/game/history?playerUsernameOrEmail=" + player;
    let response = await fetch(url, null);
    if (response.ok) {
        let list = document.getElementById("gameList");
        let gameList = await response.json();
        for (var i in gameList) {
            let game = gameList[i];
            let turn = game.whitesTurn ? "white" : "black";
            let string = game.id + ". move: " + game.moveNumber + " turn: " + turn + " status: " + game.gameStatus;
            let newItem = document.createElement("li");

            newItem.id = game.id;
            newItem.className = "historypage-li list-item";

            let spanElement = document.createElement("span");

            spanElement.textContent = string;

            newItem.appendChild(spanElement);
            newItem.addEventListener("click", function () {
                localStorage.setItem("boardId", newItem.id)
                window.location.href = "../chessBoard/chessBoard.html";
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

document.getElementById('playAgainstAiLink').addEventListener('click', function () {
    window.location.href = '../learning_page/learning_page.html';
});

function logOut() {
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}