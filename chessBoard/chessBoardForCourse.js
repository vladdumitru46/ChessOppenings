let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let informationCourseValue = urlParams.get('courseTitle');
let informationCourseElement = document.getElementById("courseContent");
let pageTitle = document.getElementById("pageTitle");
const baseUrl = "http://localhost:8080/chess";

let subCourseName = "";
async function loadData() {
    if (informationCourseElement && informationCourseValue) {
        pageTitle.innerText = informationCourseValue;

        let subcoursesString = await getSubcourses(informationCourseValue);
        let subcourses = subcoursesString.split(',');
        informationCourseElement.innerHTML = `
            <h2 id="courseTitle2">${informationCourseValue}</h2>
            <ul id="courseList">
                ${subcourses.map((subcourse, index) => `<li class="${index === 0 ? 'selected' : ''}">${subcourse}</li>`).join('')}
            </ul>
        `;

        let firstListItem = document.querySelector("#courseList li:first-child");
        subCourseName = firstListItem.innerText;
        console.log(informationCourseElement.innerText);
        await fetch(baseUrl + "/startCourse/reset", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                playerId: localStorage.getItem("player"),
                courseName: pageTitle.innerText,
                boardId: localStorage.getItem('boardId')
            })
        });
        console.log(subCourseName);
        setBoard();

        document.getElementById("courseList").addEventListener("click", async function (event) {
            if (event.target.tagName === "LI") {

                let listItems = document.querySelectorAll("#courseList li");
                listItems.forEach(item => item.classList.remove("selected"));

                event.target.classList.add("selected");

                subCourseName = event.target.innerText;

                await fetch(baseUrl + "/startCourse/reset", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        playerId: localStorage.getItem("player"),
                        courseName: pageTitle.innerText,
                        boardId: localStorage.getItem('boardId')
                    })
                });
                console.log(subCourseName);
                setBoard();
            }
        });
    }
}
loadData();
async function getSubcourses(courseName) {

    let resp = await fetch(baseUrl + "/subCourses?courseName=" + courseName, null);
    if (resp.ok) {
        const list = await resp.text();
        console.log(list)
        return list;
    } else {
        console.log("BLA BLA")
    }

}
async function setBoard() {

    let gameId = localStorage.getItem("boardId");
    let respone = await fetch(baseUrl + "/board/boardConfiguration?boardId=" + gameId, null);
    if (respone.ok) {
        let board = await respone.text();
        console.log(board)
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
                        case "king": piesa = "King"; break;
                        case "queen": piesa = "Queen"; break;
                        case "rook": piesa = "Rook"; break;
                        case "bishop": piesa = "Bishop"; break;
                        case "knight": piesa = "Knight"; break;
                        case "pawn": piesa = "Pawn"; break;
                    }
                    element.querySelector('img').src = "../pieces/" + ce[1] + piesa + ".png";
                } else if (ce != '') {
                    element.setAttribute("data-piesa", ce[1]);
                    element.querySelector('img').src = "";
                }
            }
        }
    }

}

let courseUrl = baseUrl + "/startCourse/"

let boardUrl = baseUrl + "/board/save";
console.log(boardUrl);


let square1 = null;
let square2 = null;
let highlightedSquares = [];

async function getPiece(square) {

    if (square1 == null) {
        square1 = square;

        let id = square1.getAttribute("id");

        let getAllPosibleMovesResponse = await fetch(`${baseUrl}/move/piecePossibleMoves?boardId=${localStorage.getItem('boardId')}&position=${id}`, {
            method: "GET"
        });
        if (getAllPosibleMovesResponse.ok) {
            let listOfMoves = await getAllPosibleMovesResponse.json();
            resetHighlightedSquares();
            for (var i in listOfMoves) {
                let sq = document.getElementById(listOfMoves[i]);
                const originalImageSrc = sq.querySelector('img').src; // Store original source
                highlightedSquares.push({ element: sq, originalSrc: originalImageSrc });
                sq.querySelector('img').src = "../pieces/posibleMove.svg";
            }
        }

    } else {
        square2 = square;

        resetHighlightedSquares();
    }
    var ok = 0;
    if (square1 != null && square2 != null && square1.getAttribute('data-piesa') !== "none") {

        if (square1.getAttribute('data-piesa').includes("king")) {
            const piesaSquare1 = square1.getAttribute('data-piesa');
            const imageUrlSquare1 = square1.querySelector('img').src;
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `${baseUrl}/move/castle`;
            // Construct the request object
            const requestData = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    boardId: localStorage.getItem('boardId'),
                    start: square1.getAttribute("id"),
                    end: square2.getAttribute("id"),
                    pieceColour: atributes[0]
                })

            };
            let s = square2.getAttribute('data-piesa');
            let ss = square2.querySelector('img').src;
            ok = await castle(piesaSquare1, imageUrlSquare1, springBootURL, requestData, ok)
            if (ok == 1) {
                let a = await verifyMove(square1, square2, atributes[0]);
                if (a == "nu") {
                    square2.setAttribute('data-piesa', s);
                    square2.querySelector('img').src = ss;

                    square1.setAttribute('data-piesa', piesaSquare1);
                    square1.querySelector('img').src = imageUrlSquare1;
                } else {
                    square1 = null;
                    square2 = null;
                    await computerMove()
                }
            }
        }

        if (ok == 0) {
            const piesaSquare1 = square1.getAttribute('data-piesa');
            const imageUrlSquare1 = square1.querySelector('img').src;
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `${baseUrl}/startCourse/isTheMoveLegal`;
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

            let checkMateCheck = baseUrl + "/move/checkmate"
            makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, atributes[0])
        }
    }
}
function resetHighlightedSquares() {
    for (const square of highlightedSquares) {
        const sq = square.element;
        const originalImageSrc = square.originalSrc;
        sq.querySelector('img').src = originalImageSrc;
    }
    highlightedSquares = [];
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

            var rookSquare = document.getElementById("00");
            var rookNewSquare = document.getElementById("03");
            rookNewSquare.setAttribute('data-piesa', rookSquare.getAttribute('data-piesa'));
            rookNewSquare.querySelector('img').src = rookSquare.querySelector('img').src;

            rookSquare.setAttribute('data-piesa', 'none');
            rookSquare.querySelector('img').src = "";

            ok = 1;
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    }
    else if (square2.getAttribute("id") === "06") {
        let response = await fetch(springBootURL, requestData)
        if (response.ok) {
            console.log("BA")
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

            ok = 1;
            console.log(ok)
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    }
    else if (square2.getAttribute("id") === "72") {
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

            ok = 1;
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    }
    else if (square2.getAttribute("id") === "76") {
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
            ok = 1;
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!")
        }

    }
    return ok;
}


async function makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, colour) {
    try {

        let s = square2.getAttribute('data-piesa');
        let ss = square2.querySelector('img').src;
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
                    let promotePawnUrl = baseUrl + "/move/promote";
                    console.log(promotePawnUrl);
                    response4 = await fetch(promotePawnUrl, {
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
                        console.log("sunt unde trebuie")
                        square2.setAttribute('data-piesa', piesaSquare1);
                        square2.querySelector('img').src = selectedImageSrc;

                        square1.setAttribute('data-piesa', 'none');
                        square1.querySelector('img').src = "";

                        ok = 1;
                    }
                }
                console.log("am iesit de aici: " + ok)

            }
            if (ok == 1) {
                let a = await verifyMove(square1, square2, colour);
                if (a == "nu") {
                    square2.setAttribute('data-piesa', s);
                    square2.querySelector('img').src = ss;

                    square1.setAttribute('data-piesa', piesaSquare1);
                    square1.querySelector('img').src = imageUrlSquare1;
                    square1 = null;
                    square2 = null;
                } else {
                    square1 = null;
                    square2 = null;
                    await computerMove();
                }
            }

        }
        if (ok == 0) {
            const response1 = await fetch(springBootURL, requestData);

            if (response1.ok) {
                square2.setAttribute('data-piesa', piesaSquare1);
                square2.querySelector('img').src = imageUrlSquare1;

                square1.setAttribute('data-piesa', 'none');
                square1.querySelector('img').src = "";


                let a = await verifyMove(square1, square2, colour);
                if (a == "nu") {
                    square2.setAttribute('data-piesa', s);
                    square2.querySelector('img').src = ss;

                    square1.setAttribute('data-piesa', piesaSquare1);
                    square1.querySelector('img').src = imageUrlSquare1;
                    square1 = null;
                    square2 = null;
                } else {
                    square1 = null;
                    square2 = null;
                    const response2 = await fetch(checkMateCheck, {
                        method: "POST",
                        body: JSON.stringify({
                            gameId: localStorage.getItem('boardId')
                        })
                    });

                    if (response2.ok) {
                        let res = await response2.text();
                        if (res !== "continue") {
                            window.alert(res);
                        }
                    } else {
                        // handle other cases if needed
                        square1 = null;
                        square2 = null;
                    }
                    if (a !== "finish") {
                        await computerMove();
                    }
                }
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


async function verifyMove(square1, square2, atributes) {
    let url = courseUrl + "verifyMove";
    console.log("course " + subCourseName)
    const requestData = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            playerUsernameOrEmail: localStorage.getItem("player"),
            subCourseName: subCourseName,
            courseName: pageTitle.innerText,
            boardId: localStorage.getItem('boardId'),
            start: square1.getAttribute("id"),
            end: square2.getAttribute("id"),
            pieceColour: atributes
        })
    };

    let verifyMoveRequest = await fetch(url, requestData);
    let res = await verifyMoveRequest.text();
    console.log(res)
    if (res === "You have finished the course!") {
        window.alert(res);
        return "finish"
    }
    if (!verifyMoveRequest.ok) {

        return "nu"
    }
}

async function computerMove() {
    let url = courseUrl + "computerMove?courseName=" + pageTitle.innerText + "&subCourseName=" + subCourseName + "&boardId=" + localStorage.getItem('boardId') + "&userName=" + localStorage.getItem("player");

    let computerMoveResponse = await fetch(url, null);
    if (computerMoveResponse.ok) {
        let res = await computerMoveResponse.text();

        let move = res.split(" ");
        console.log(move)
        let s1 = document.getElementById(move[0]);
        let s2 = document.getElementById(move[1]);

        const piesaSquare1 = s1.getAttribute('data-piesa');
        const imageUrlSquare1 = s1.querySelector('img').src;

        console.log(imageUrlSquare1)
        s2.setAttribute('data-piesa', piesaSquare1);
        s2.querySelector('img').src = imageUrlSquare1;

        s1.setAttribute('data-piesa', 'none');
        s1.querySelector('img').src = "";
        console.log(s2.querySelector('img').src)
    } else {

        let res = await computerMoveResponse.text();
        console.log(res);
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
    window.location.href = '../chessBoard/chessBoard.html';
});

function logOut() {
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}

async function getHint() {
    const requestData = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            playerUsernameOrEmail: localStorage.getItem("player"),
            subCourseName: subCourseName,
            courseName: pageTitle.innerText,
            boardId: localStorage.getItem('boardId')
        })
    };
    let url = baseUrl + "/startCourse/hint";
    let resp = await fetch(url, requestData);
    if(resp.ok){
        let hint = await resp.text();
        window.alert(hint);
    }
}