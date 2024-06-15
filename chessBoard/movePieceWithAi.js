let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let newGame = urlParams.get('newGame');
let pageTitle = document.getElementById("pageTitle");
let player = localStorage.getItem("player")
const baseUrl = "http://localhost:8080/chess";
pageTitle.innerText = player + " vs AI";
let boardUrl = baseUrl + "/game";
console.log(boardUrl);
if (newGame) {
    fetchData();
} else {
    setBoard();
}

let moveNumber = 0;
let maxMoveNumber = 0;

async function fetchData() {
    let player = localStorage.getItem("player")
    let playerColour = localStorage.getItem("playerColour")
    console.log(boardUrl);

    try {
        const res = await fetch(boardUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                playerEmailOrUsername: player,
                playerColour: localStorage.getItem("playerColour")
            })
        });
        if (res.ok) {
            try {
                let id = await res.text();
                console.log('ID:', id);
                localStorage.setItem('boardId', id);

                let gameId = localStorage.getItem("boardId");
                let r = await fetch(baseUrl + "/game/getMoveNumber?gameId=" + gameId, null);
                if (r.ok) {
                    moveNumber = await r.text();
                    if (playerColour == "black") {
                        await moveAi();
                    }
                }
            } catch (error) {
                console.error('Eroare la extragerea textului:', error);
            }
        } else {
            console.log('Răspuns incorect:', res.status);
        }
    } catch (error) {
        console.error('Eroare la solicitarea de rețea:', error);
    }
}

async function boardSetter(respone, gameId) {
    let board = await respone.text();
    let bb = board.split(" + ");
    let whitesTurn = bb[1];
    console.log("whites turn : " + whitesTurn);
    board = bb[0].split('\n')
    for (var row in board) {
        let cell = board[row].split(",")
        for (var c in cell) {
            let ce = cell[c].split(" ")
            let element = document.getElementById(ce[0]);
            if (ce.length > 2) {
                element.setAttribute("data-piesa", ce[1] + " " + ce[2])
                let piesa;
                switch (ce[2]) {
                    case "king":
                        piesa = "King";
                        break;
                    case "queen":
                        piesa = "Queen";
                        break;
                    case "rook":
                        piesa = "Rook";
                        break;
                    case "bishop":
                        piesa = "Bishop";
                        break;
                    case "knight":
                        piesa = "Knight";
                        break;
                    case "pawn":
                        piesa = "Pawn";
                        break;
                }
                element.querySelector('img').src = "../pieces/" + ce[1] + piesa + ".png";

            } else if (ce != '') {
                element.setAttribute("data-piesa", ce[1]);
                element.querySelector('img').src = "";
            }
        }
    }

    let response1 = await fetch(baseUrl + "/game/moves?gameId=" + gameId, null);
    if (response1.ok) {
        let r = await response1.text();
        let moves = r.split(",")

        let ulElement = document.getElementById("courseContent");
        ulElement.innerHTML = "";
        for (let i = 1; i < moves.length; i++) {
            let newItem = document.createElement("li");

            let spanElement = document.createElement("span");
            let m = '';
            if (moves[i].charAt(moves[i].length - 1) === ']') {
                m = moves[i].slice(1, moves[i].length - 2);
            } else {
                m = moves[i].slice(1, moves[i].length - 1);
            }
            spanElement.textContent = m;

            newItem.addEventListener("click", function () {
                let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;
                getMovesHistory(orderNumber)
            });

            newItem.appendChild(spanElement);
            ulElement.appendChild(newItem);
            ulElement.scrollTop = ulElement.scrollHeight;
        }

        let gameId = localStorage.getItem("boardId");
        console.log(baseUrl + "/game/getMoveNumber?gameId=" + gameId)
        let re = await fetch(baseUrl + "/game/getMoveNumber?gameId=" + gameId, null);
        if (re.ok) {
            moveNumber = await re.text();
            maxMoveNumber = moveNumber;
        }
    }
    let playerColour = localStorage.getItem("playerColour");
    console.log(playerColour)
    if (whitesTurn == "false" && playerColour == "white") {
        await moveAi();
    } else if (whitesTurn == "true" && playerColour == "black") {
        await moveAi();
    }
}

async function setBoard() {
    let gameId = localStorage.getItem("boardId");
    let respone = await fetch(baseUrl + "/board/getBoardConfiguration?gameId=" + gameId, null);
    if (respone.ok) {
        await boardSetter(respone, gameId);
    }

}

let square1 = null;
let square2 = null;
let highlightedSquares = [];

async function getPiece(square) {

    if (square1 == null) {
        square1 = square;

        let id = square1.getAttribute("id");

        let getAllPossibleMovesResponse = await fetch(`${baseUrl}/move/piecePossibleMoves?boardId=${localStorage.getItem('boardId')}&position=${id}`, {
            method: "GET"
        });
        if (getAllPossibleMovesResponse.ok) {
            let listOfMoves = await getAllPossibleMovesResponse.json();
            resetHighlightedSquares();
            for (let i in listOfMoves) {
                let sq = document.getElementById(listOfMoves[i]);
                const originalImageSrc = sq.querySelector('img').src;
                console.log(sq.querySelector("img").src)
                highlightedSquares.push({ element: sq, originalSrc: originalImageSrc });
                if (originalImageSrc) {
                    sq.querySelector('img').src = "../pieces/posibleMove.svg";
                }
            }
        }

    } else {
        square2 = square;

        resetHighlightedSquares();
    }
    let ok = 0;
    if (square1 != null && square2 != null && square1.getAttribute('data-piesa') !== "none") {

        if (square1.getAttribute('data-piesa').includes("king") && square1.getAttribute("id") === "04") {
            const piesaSquare1 = square1.getAttribute('data-piesa');
            const imageUrlSquare1 = square1.querySelector('img').src;
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `${baseUrl}/move/castle`;

            const requestData = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    gameId: localStorage.getItem('boardId'),
                    start: square1.getAttribute("id"),
                    end: square2.getAttribute("id"),
                    pieceColour: atributes[0]
                })

            };
            ok = await castle(piesaSquare1, imageUrlSquare1, springBootURL, requestData, ok)
        }

        if (ok === 0) {
            const piesaSquare1 = square1.getAttribute('data-piesa');
            const imageUrlSquare1 = square1.querySelector('img').src;
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `${baseUrl}/move/${atributes[1]}`;
            console.log(springBootURL);

            const requestData = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    gameId: localStorage.getItem('boardId'),
                    start: square1.getAttribute("id"),
                    end: square2.getAttribute("id"),
                    pieceColour: atributes[0]
                })

            };

            await makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, atributes[0])
        }
    }
}
function resetHighlightedSquares() {
    for (const square of highlightedSquares) {
        const sq = square.element;
        sq.querySelector('img').src = square.originalSrc;
    }
    highlightedSquares = [];
}

async function getMovesHistory(mN) {
    let gameId = localStorage.getItem("boardId")
    let moveHistoryUrl = baseUrl + "/game/movesHistory?gameId=" + gameId + "&moveNumber=" + mN;
    let response = await fetch(moveHistoryUrl, null);
    if (response.ok) {
        let board = await response.text();
        await setNewBoard(board)
        moveNumber = mN * 2;
    }
}

async function setNewBoard(board) {

    board = board.split('\n')
    for (var row in board) {
        let cell = board[row].split(",")
        for (var c in cell) {
            let ce = cell[c].split(" ")
            let element = document.getElementById(ce[0]);
            if (ce.length > 2) {
                element.setAttribute("data-piesa", ce[1] + " " + ce[2])
                let piesa;
                switch (ce[2]) {
                    case "king":
                        piesa = "King";
                        break;
                    case "queen":
                        piesa = "Queen";
                        break;
                    case "rook":
                        piesa = "Rook";
                        break;
                    case "bishop":
                        piesa = "Bishop";
                        break;
                    case "knight":
                        piesa = "Knight";
                        break;
                    case "pawn":
                        piesa = "Pawn";
                        break;
                }
                element.querySelector('img').src = "../pieces/" + ce[1] + piesa + ".png";
            } else if (ce != '') {
                element.setAttribute("data-piesa", ce[1]);
                element.querySelector('img').src = "";
            }
        }

    }
}


async function goBack() {
    console.log("move number: " + moveNumber)
    if (moveNumber > 0) {
        moveNumber -= 1;
        let gameId = localStorage.getItem("boardId")
        let moveHistoryUrl = baseUrl + "/game/moveBefore?gameId=" + gameId + "&moveNumber=" + moveNumber;
        let response1 = await fetch(moveHistoryUrl, null);
        if (response1.ok) {
            let board = await response1.text();
            await setNewBoard(board)
        }
    }
}


async function goForward() {
    console.log("intru aici?")
    console.log(maxMoveNumber)
    if (moveNumber < maxMoveNumber) {
        moveNumber += 1;
        console.log("move number: " + moveNumber)
        let gameId = localStorage.getItem("boardId")
        let moveHistoryUrl = baseUrl + "/game/moveForward?gameId=" + gameId + "&moveNumber=" + moveNumber;
        console.log(moveHistoryUrl)
        let response1 = await fetch(moveHistoryUrl, null);
        if (response1.ok) {
            let board = await response1.text();
            await setNewBoard(board)
        }
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
    window.location.href = '../learning_page/learning_page.html';
});

document.getElementById('gameHistoryLink').addEventListener('click', function () {
    window.location.href = "../history_page/historypage.html";
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