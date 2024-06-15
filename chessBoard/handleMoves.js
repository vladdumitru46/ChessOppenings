async function castle(piesaSquare1, imageUrlSquare1, springBootURL, requestData, ok) {
    console.log(springBootURL);
    if (square2.getAttribute("id") === "02") {
        let response = await fetch(springBootURL, requestData);
        if (response.ok) {

            let rookSquare = document.getElementById("00");
            let rookNewSquare = document.getElementById("03");

            ok = await movePiecesForCastle(piesaSquare1, imageUrlSquare1, rookNewSquare, rookSquare, ok, response);
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!");
        }

    } else if (square2.getAttribute("id") === "06") {
        let response = await fetch(springBootURL, requestData);
        if (response.ok) {

            let rookSquare = document.getElementById("07");
            let rookNewSquare = document.getElementById("05");

            ok = await movePiecesForCastle(piesaSquare1, imageUrlSquare1, rookNewSquare, rookSquare, ok, response);
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!");
        }
    }
    return ok;
}

let m = 1;

async function movePiecesForCastle(piesaSquare1, imageUrlSquare1, rookNewSquare, rookSquare, ok, response) {
    let gameId = localStorage.getItem("boardId");
    await boardSetter(response, gameId);
    await checkMateCheck();
    return ok;
}

async function makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, colour) {
    try {
        let ok = 0;
        if (square1.getAttribute('data-piesa').includes("pawn")) {

            let coordinates = square2.getAttribute("id");

            if (coordinates[0] === "0" && colour === "black" || coordinates[0] === "7" && colour === "white") {

                let selectedImageSrc = null;

                let imageContainer = document.createElement('div');
                imageContainer.id = "promotePawn";
                imageContainer.style.position = 'absolute'; // Asigură poziționarea absolută
                imageContainer.style.zIndex = '1000'; // Asigură că containerul va fi deasupra altor elemente

                ["Queen", "Rook", "Bishop", "Knight"].forEach(piece => {

                    let imgElement = document.createElement('img');
                    imgElement.src = `../pieces/${colour}${piece}.png`;
                    imgElement.addEventListener('click', function () {
                        selectedImageSrc = imgElement.src;
                    });
                    imageContainer.appendChild(imgElement);
                });
                square2.parentNode.insertBefore(imageContainer, square2.nextSibling);

                // Calculează poziția pătratului apăsat și poziționează containerul
                let rect = square2.getBoundingClientRect();
                imageContainer.style.left = `${rect.right}px`; // Poziționează la dreapta pătratului
                imageContainer.style.top = `${rect.top}px`; // Aliniază vertical cu pătratul


                const imageSelectionPromise = new Promise((resolve) => {
                    const imageClickHandler = function (event) {
                        selectedImageSrc = event.target.src;
                        imageContainer.removeEventListener('click', imageClickHandler);
                        resolve();
                    };

                    imageContainer.addEventListener('click', imageClickHandler);
                });
                await imageSelectionPromise;

                imageContainer.remove();
                let newPiece = "";
                if (selectedImageSrc.includes("Queen")) {
                    newPiece = "Queen";
                } else if (selectedImageSrc.includes("Rook")) {
                    newPiece = "Rook";
                } else if (selectedImageSrc.includes("Bishop")) {
                    newPiece = "Bishop";
                } else if (selectedImageSrc.includes("Knight")) {
                    newPiece = "Knight";
                }
                const requestData = {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        gameId: localStorage.getItem('boardId'),
                        start: square1.getAttribute("id"),
                        end: square2.getAttribute("id"),
                        pieceColour: colour,
                        newPiece: newPiece
                    })

                };
                const response1 = await fetch(springBootURL, requestData);

                if (response1.ok) {
                    ok = 1;
                    let gameId = localStorage.getItem("boardId");
                    await boardSetter(response1, gameId);
                    m++;
                    moveNumber++;
                    square1 = null;
                    square2 = null;
                    await checkMateCheck();
                }
            }

        }
        // }
        if (ok === 0) {
            const response1 = await fetch(springBootURL, requestData);

            if (response1.ok) {
                let gameId = localStorage.getItem("boardId");
                await boardSetter(response1, gameId);
                m++;
                moveNumber++;
                square1 = null;
                square2 = null;

                await checkMateCheck();

                // await moveAi();
            } else {
                square1 = null;
                square2 = null;
                let k = await response1.text();
                // console.log("cannot move the piece there! check the backend logs for more information!");
                console.log(k);
            }
        }


    } catch (error) {
        console.error("Error:", error);
    }
}

function updateMoveList(res) {
    let ulElement = document.getElementById("courseContent");
    let newItem = document.createElement("li");

    newItem.className = "historypage-li list-item";

    let spanElement = document.createElement("span");

    spanElement.textContent = res;


    newItem.addEventListener("click", function () {
        let orderNumber = Array.from(this.parentElement.children).indexOf(this) + 1;

        getMovesHistory(orderNumber);
    });
    newItem.appendChild(spanElement);
    ulElement.appendChild(newItem);
}

async function moveAi() {
    let ai = baseUrl + "/move/ai/makeMove";
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
        let gameId = localStorage.getItem("boardId");
        await boardSetter(response3, gameId);
        await checkMateCheck();

    }
}

async function checkMateCheck() {
    let gameId = localStorage.getItem("boardId");
    let r = await fetch(baseUrl + "/game/getMoveNumber?gameId=" + gameId, null);
    if (r.ok) {
        moveNumber = await r.text();
        maxMoveNumber = moveNumber;
    }
    // let newNr = moveNumber / 2;
    // await getMovesHistory(newNr);
    let checkMateCheckUrl = baseUrl + "/move/checkmate";
    const response2 = await fetch(checkMateCheckUrl, {
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

    }
}
