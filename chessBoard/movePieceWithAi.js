let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let newGame = urlParams.get('newGame');
let pageTitle = document.getElementById("pageTitle");
let player = localStorage.getItem("player")
pageTitle.innerText = player + " vs AI";
let boardUrl = "http://localhost:8080/chess/game";
console.log(boardUrl);
if (newGame) {
    fetchData()
} else {
    setBoard();
}

let moveNumber = 0;

async function fetchData() {
    let player = localStorage.getItem("player")
    console.log(boardUrl);

    try {
        let r = await fetch(boardUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                playerEmailOrUsername: player

            })
        });

        if (r.ok) {
            try {
                let id = await r.text();
                console.log('ID:', id);
                localStorage.setItem('boardId', id);

                let gameId = localStorage.getItem("boardId");
                let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
                if (r.ok) {
                    moveNumber = await r.text();
                }
            } catch (error) {
                console.error('Eroare la extragerea textului:', error);
            }
        } else {
            console.log('Răspuns incorect:', r.status);
        }
    } catch (error) {
        console.error('Eroare la solicitarea de rețea:', error);
    }
}

// fetchData();
async function setBoard() {
    let gameId = localStorage.getItem("boardId");
    let respone = await fetch("http://localhost:8080/chess/board/getBoardConfiguration?gameId=" + gameId, null);
    if (respone.ok) {
        let board = await respone.text();
        board = board.split('\n')
        for (var row in board) {
            let cell = board[row].split(",")
            for (var c in cell) {
                let ce = cell[c].split(" ")
                // console.log(ce)
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

        let response1 = await fetch("http://localhost:8080/chess/game/moves?gameId=" + gameId, null);
        if (response1.ok) {
            let r = await response1.text();
            let moves = r.split(",")

            for (let i = 1; i < moves.length; i++) {
                let ulElement = document.getElementById("courseContent");
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
            }

            let gameId = localStorage.getItem("boardId");
            let re = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
            if (re.ok) {
                moveNumber = await re.text();
            }
        }

    }

}

let square1 = null;
let square2 = null;

async function getPiece(square) {

    if (square1 == null) {
        square1 = square;
    } else {
        square2 = square;
    }
    let ok = 0;
    if (square1 != null && square2 != null && square1.getAttribute('data-piesa') !== "none") {

        if (square1.getAttribute('data-piesa').includes("king")) {
            const piesaSquare1 = square1.getAttribute('data-piesa');
            const imageUrlSquare1 = square1.querySelector('img').src;
            const springBootPort = 8080;
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `http://localhost:${springBootPort}/chess/move/castle`;

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
            const springBootPort = 8080; // Replace with the actual port number
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `http://localhost:${springBootPort}/chess/move/${atributes[1]}`;
            console.log(springBootURL);

            // Construct the request object
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

            let checkMateCheck = "http://localhost:8080/chess/move/checkmate"
            makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, atributes[0])
        }
    }
}

async function castle(piesaSquare1, imageUrlSquare1, springBootURL, requestData, ok) {
    console.log(springBootURL)
    if (square2.getAttribute("id") === "02") {
        let response = await fetch(springBootURL, requestData)
        if (response.ok) {
            square2.setAttribute('data-piesa', piesaSquare1);
            square2.querySelector('img').src = imageUrlSquare1;

            square1.setAttribute('data-piesa', 'none');
            square1.querySelector('img').src = "";

            let rookSquare = document.getElementById("00");
            let rookNewSquare = document.getElementById("03");
            rookNewSquare.setAttribute('data-piesa', rookSquare.getAttribute('data-piesa'));
            rookNewSquare.querySelector('img').src = rookSquare.querySelector('img').src;

            rookSquare.setAttribute('data-piesa', 'none');
            rookSquare.querySelector('img').src = "";

            square1 = null;
            square2 = null;
            ok = 1;
            let res = await response.text();
            // let element = document.getElementById("courseContent");
            // element.innerText = element.innerText + '\n' + m + '.' + ' ' + res;

            let ulElement = document.getElementById("courseContent");
            let newItem = document.createElement("li");

            newItem.className = "historypage-li list-item";

            let spanElement = document.createElement("span");

            spanElement.textContent = res;

            newItem.addEventListener("click", function () {
                let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;
                getMovesHistory(orderNumber)
            });

            newItem.appendChild(spanElement);
            ulElement.appendChild(newItem);

            let gameId = localStorage.getItem("boardId");
            let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
            if (r.ok) {
                moveNumber = await r.text();
            }
            m++;
            await moveAi();
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    } else if (square2.getAttribute("id") === "06") {
        let response = await fetch(springBootURL, requestData)
        if (response.ok) {
            square2.setAttribute('data-piesa', piesaSquare1);
            square2.querySelector('img').src = imageUrlSquare1;

            square1.setAttribute('data-piesa', 'none');
            square1.querySelector('img').src = "";

            var rookSquare = document.getElementById("07");
            var rookNewSquare = document.getElementById("05");
            rookNewSquare.setAttribute('data-piesa', rookSquare.getAttribute('data-piesa'));
            rookNewSquare.querySelector('img').src = rookSquare.querySelector('img').src;

            rookSquare.setAttribute('data-piesa', 'none');
            rookSquare.querySelector('img').src = "";

            square1 = null;
            square2 = null;
            ok = 1;
            let res = await response.text();
            // let element = document.getElementById("courseContent");
            // element.innerText = element.innerText + '\n' + m + '.' + ' ' + res;

            let ulElement = document.getElementById("courseContent");
            let newItem = document.createElement("li");

            newItem.className = "historypage-li list-item";

            let spanElement = document.createElement("span");

            spanElement.textContent = res;

            newItem.addEventListener("click", function () {
                // Obține numărul de ordine al elementului în lista ordonată
                let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;

                getMovesHistory(orderNumber)
            });

            newItem.appendChild(spanElement);
            ulElement.appendChild(newItem);

            m++;

            let gameId = localStorage.getItem("boardId");
            let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
            if (r.ok) {
                moveNumber = await r.text();
            }
            await moveAi();
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    } else if (square2.getAttribute("id") === "72") {
        let response = await fetch(springBootURL, requestData)
        if (response.ok) {
            square2.setAttribute('data-piesa', piesaSquare1);
            square2.querySelector('img').src = imageUrlSquare1;
            var rookSquare = document.getElementById("70");
            var rookNewSquare = document.getElementById("73");
            rookNewSquare.setAttribute('data-piesa', rookSquare.getAttribute('data-piesa'));
            rookNewSquare.querySelector('img').src = rookSquare.querySelector('img').src;

            rookSquare.setAttribute('data-piesa', 'none');
            rookSquare.querySelector('img').src = "";

            square1.setAttribute('data-piesa', 'none');
            square1.querySelector('img').src = "";

            square1 = null;
            square2 = null;
            ok = 1;
            let res = await response.text();
            // let element = document.getElementById("courseContent");
            // element.innerText = element.innerText + '\n' + m + '.' + ' ' + res;

            let ulElement = document.getElementById("courseContent");
            let newItem = document.createElement("li");

            newItem.className = "historypage-li list-item";

            let spanElement = document.createElement("span");

            spanElement.textContent = res;

            newItem.addEventListener("click", function () {
                // Obține numărul de ordine al elementului în lista ordonată
                let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;

                getMovesHistory(orderNumber)
            });
            newItem.appendChild(spanElement);
            ulElement.appendChild(newItem);

            let gameId = localStorage.getItem("boardId");
            let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
            if (r.ok) {
                moveNumber = await r.text();
            }
            m++;
            await moveAi();
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    } else if (square2.getAttribute("id") === "76") {
        let response = await fetch(springBootURL, requestData)
        if (response.ok) {
            square2.setAttribute('data-piesa', piesaSquare1);
            square2.querySelector('img').src = imageUrlSquare1;

            square1.setAttribute('data-piesa', 'none');
            square1.querySelector('img').src = "";

            var rookSquare = document.getElementById("77");
            var rookNewSquare = document.getElementById("75");
            rookNewSquare.setAttribute('data-piesa', rookSquare.getAttribute('data-piesa'));
            rookNewSquare.querySelector('img').src = rookSquare.querySelector('img').src;

            rookSquare.setAttribute('data-piesa', 'none');
            rookSquare.querySelector('img').src = "";
            square1 = null;
            square2 = null;
            ok = 1;
            let res = await response.text();
            // let element = document.getElementById("courseContent");
            // element.innerText = element.innerText + '\n' + m + '.' + ' ' + res;

            let ulElement = document.getElementById("courseContent");
            let newItem = document.createElement("li");

            newItem.className = "historypage-li list-item";

            let spanElement = document.createElement("span");

            spanElement.textContent = res;

            newItem.addEventListener("click", function () {
                // Obține numărul de ordine al elementului în lista ordonată
                let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;

                getMovesHistory(orderNumber)
            });
            newItem.appendChild(spanElement);
            ulElement.appendChild(newItem);

            m++;

            let gameId = localStorage.getItem("boardId");
            let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
            if (r.ok) {
                moveNumber = await r.text();
            }
            await moveAi();
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    }
    return ok;
}

let m = 1;

async function makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, colour) {
    try {
        let ok = 0;
        if (square1.getAttribute('data-piesa').includes("pawn")) {
            let coordinates = square2.getAttribute("id");
            if (coordinates[0] === "0" || coordinates[0] === "7") {

                const response1 = await fetch(springBootURL, requestData);

                if (response1.ok) {
                    let selectedImageSrc = null;
                    let imageContainer = document.createElement('div');
                    imageContainer.id = "promotePawn";

                    ["Queen", "Rook", "Bishop", "Knight"].forEach(piece => {
                        let imgElement = document.createElement('img');
                        imgElement.src = `../pieces/${colour}${piece}.png`;
                        imgElement.addEventListener('click', function () {
                            selectedImageSrc = imgElement.src;
                        });
                        imageContainer.appendChild(imgElement);
                    });


                    square2.parentNode.insertBefore(imageContainer, square2.nextSibling);
                    const imageSelectionPromise = new Promise((resolve) => {
                        const imageClickHandler = function (event) {
                            selectedImageSrc = event.target.src;
                            imageContainer.removeEventListener('click', imageClickHandler);
                            resolve();
                        };

                        imageContainer.addEventListener('click', imageClickHandler);
                    });

                    // Așteaptă ca utilizatorul să aleagă o imagine înainte de a continua
                    await imageSelectionPromise;
                    imageContainer.remove();


                    let newPiece = "";
                    piesaSquare1 = "";
                    if (selectedImageSrc.includes("Queen")) {
                        newPiece = "Queen";
                        piesaSquare1 = `${colour} queen`;
                    } else if (selectedImageSrc.includes("Rook")) {
                        newPiece = "Rook";
                        piesaSquare1 = `${colour} rook`;
                    } else if (selectedImageSrc.includes("Bishop")) {
                        newPiece = "Bishop";
                        piesaSquare1 = `${colour} bishop`;
                    } else if (selectedImageSrc.includes("Knight")) {
                        newPiece = "Horse";
                        piesaSquare1 = `${colour} knight`;
                    }

                    let promotePawnUrl = "http://localhost:8080/chess/move/promote";
                    console.log(promotePawnUrl);
                    let response4 = await fetch(promotePawnUrl, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify({
                            gameId: localStorage.getItem('boardId'),
                            newPiece: newPiece,
                            coordinates: square2.getAttribute("id")
                        })
                    })
                    if (response4.ok) {
                        square2.setAttribute('data-piesa', piesaSquare1);
                        square2.querySelector('img').src = selectedImageSrc;

                        square1.setAttribute('data-piesa', 'none');
                        square1.querySelector('img').src = "";
                        square1 = null;
                        square2 = null;

                        ok = 1;
                        let res = await response4.text()

                        let ulElement = document.getElementById("courseContent");
                        let newItem = document.createElement("li");

                        newItem.className = "historypage-li list-item";

                        let spanElement = document.createElement("span");

                        spanElement.textContent = res;

                        newItem.addEventListener("click", function () {
                            // Obține numărul de ordine al elementului în lista ordonată
                            let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;

                            getMovesHistory(orderNumber)
                        });
                        newItem.appendChild(spanElement);
                        ulElement.appendChild(newItem);

                        let gameId = localStorage.getItem("boardId");
                        let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
                        if (r.ok) {
                            moveNumber = await r.text();
                        }
                        m++;
                        await moveAi();
                    }
                }

            }
        }
        if (ok === 0) {
            const response1 = await fetch(springBootURL, requestData);

            if (response1.ok) {
                square2.setAttribute('data-piesa', piesaSquare1);
                square2.querySelector('img').src = imageUrlSquare1;

                square1.setAttribute('data-piesa', 'none');
                square1.querySelector('img').src = "";

                let res = await response1.text();
                // let element = document.getElementById("courseContent");
                // element.innerText = element.innerText + '\n' + m + '.' + ' ' + res;

                let ulElement = document.getElementById("courseContent");
                let newItem = document.createElement("li");

                newItem.className = "historypage-li list-item";

                let spanElement = document.createElement("span");

                spanElement.textContent = res;


                newItem.addEventListener("click", function () {
                    // Obține numărul de ordine al elementului în lista ordonată
                    let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;

                    getMovesHistory(orderNumber)
                });
                newItem.appendChild(spanElement);
                ulElement.appendChild(newItem);

                m++;
                square1 = null;
                square2 = null;

                let gameId = localStorage.getItem("boardId");
                let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
                if (r.ok) {
                    moveNumber = await r.text();
                }
                const response2 = await fetch(checkMateCheck, {
                    method: "POST",
                    body: JSON.stringify({
                        boardId: localStorage.getItem('boardId')
                    })
                });

                if (response2.ok) {
                    let res = await response2.text();
                    console.log(res)
                    if (res !== "continue") {
                        window.alert(res);
                    }
                } else {
                    // handle other cases if needed
                }

                await moveAi();
            } else {
                square1 = null;
                square2 = null;
                console.log("cannot move the piece there! check the backend logs for more information!");
            }
        }


    } catch (error) {
        console.error("Error:", error);
    }
}

async function moveAi() {
    let ai = "http://localhost:8080/chess/move/ai/makeMove";
    let checkMateCheck = "http://localhost:8080/chess/move/checkmate"
    console.log(ai);
    let response3 = await fetch(ai, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            boardId: localStorage.getItem('boardId')
        })
    });
    if (response3.ok) {
        let bestMove = await response3.text();

        let coordonates = bestMove.split(" ");
        let coordonatesSquare1 = coordonates[0];
        let coordonatesSquare2 = coordonates[1];

        console.log(coordonatesSquare1 + " " + coordonatesSquare2);

        square1 = document.getElementById(coordonatesSquare1);
        square2 = document.getElementById(coordonatesSquare2);

        imageUrlSquare1 = square1.querySelector('img').src;
        piesaSquare1 = square1.querySelector("data-piesa")
        square2.setAttribute('data-piesa', piesaSquare1);
        square2.querySelector('img').src = imageUrlSquare1;

        square1.setAttribute('data-piesa', 'none');
        square1.querySelector('img').src = "";

        square1 = null;
        square2 = null;
        // let element = document.getElementById("courseContent");
        // element.innerText = element.innerText + "    " + coordonates[2];
        // let ulElement = document.getElementById("courseContent");

        let lastLi = document.querySelector('#courseContent li:last-child');

        // Verifică dacă există deja un <span> în ultimul <li>
        let spanElement = lastLi.querySelector('span');

        // Dacă nu există, creează unul
        if (!spanElement) {
            spanElement = document.createElement("span");
            lastLi.appendChild(spanElement);
        }

        // Adaugă coordonates[2] la textul deja existent
        spanElement.textContent += " " + coordonates[2];

        const response2 = await fetch(checkMateCheck, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                boardId: localStorage.getItem('boardId')
            })
        });

        if (response2.ok) {
            let res = await response2.text();
            if (res !== "continue") {
                window.alert(res);
            }
            let gameId = localStorage.getItem("boardId");
            let r = await fetch("http://localhost:8080/chess/game/getMoveNumber?gameId=" + gameId, null);
            if (r.ok) {
                moveNumber = await r.text();
            }

        } else {
            // handle other cases if needed
        }

    } else {
        // Handle other cases if needed
    }
}


async function getMovesHistory(moveNumber) {
    let gameId = localStorage.getItem("boardId")
    let moveHistoryUrl = "http://localhost:8080/chess/game/movesHistory?gameId=" + gameId + "&moveNumber=" + moveNumber;
    let response = await fetch(moveHistoryUrl, null);
    if (response.ok) {
        let board = await response.text();
        await setNewBoard(board)
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
    moveNumber -= 1;
    let gameId = localStorage.getItem("boardId")
    let moveHistoryUrl = "http://localhost:8080/chess/game/moveBefore?gameId=" + gameId + "&moveNumber=" + moveNumber;
    let response1 = await fetch(moveHistoryUrl, null);
    if (response1.ok) {
        let board = await response1.text();
        await setNewBoard(board)
    }

}

function goForward() {
    let gameId = localStorage.getItem("boardId")

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

function logOut() {
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}