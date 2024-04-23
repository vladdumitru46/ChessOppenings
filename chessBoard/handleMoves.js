
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
    square2.setAttribute('data-piesa', piesaSquare1);
    square2.querySelector('img').src = imageUrlSquare1;

    square1.setAttribute('data-piesa', 'none');
    square1.querySelector('img').src = "";

    rookNewSquare.setAttribute('data-piesa', rookSquare.getAttribute('data-piesa'));
    rookNewSquare.querySelector('img').src = rookSquare.querySelector('img').src;

    rookSquare.setAttribute('data-piesa', 'none');
    rookSquare.querySelector('img').src = "";

    square1 = null;
    square2 = null;
    ok = 1;

    let res = await response.text();

    updateMoveList(res);

    await checkMateCheck();
    await moveAi();
    return ok;
}

async function makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, colour) {
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
                    });
                    if (response4.ok) {
                        square2.setAttribute('data-piesa', piesaSquare1);
                        square2.querySelector('img').src = selectedImageSrc;

                        square1.setAttribute('data-piesa', 'none');
                        square1.querySelector('img').src = "";
                        square1 = null;
                        square2 = null;

                        ok = 1;
                        let res = await response4.text();

                        updateMoveList(res);

                        await checkMateCheck();
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

                updateMoveList(res);

                m++;
                moveNumber++;
                square1 = null;
                square2 = null;

                await checkMateCheck();

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
        let bestMove = await response3.text();

        let coordonates = bestMove.split(" ");
        let coordonatesSquare1 = coordonates[0];
        let coordonatesSquare2 = coordonates[1];

        console.log(coordonatesSquare1 + " " + coordonatesSquare2);

        square1 = document.getElementById(coordonatesSquare1);
        square2 = document.getElementById(coordonatesSquare2);

        imageUrlSquare1 = square1.querySelector('img').src;
        piesaSquare1 = square1.querySelector("data-piesa");
        square2.setAttribute('data-piesa', piesaSquare1);
        square2.querySelector('img').src = imageUrlSquare1;

        square1.setAttribute('data-piesa', 'none');
        square1.querySelector('img').src = "";

        square1 = null;
        square2 = null;;

        let lastLi = document.querySelector('#courseContent li:last-child');

        let spanElement = lastLi.querySelector('span');

        if (!spanElement) {
            spanElement = document.createElement("span");
            lastLi.appendChild(spanElement);
        }

        spanElement.textContent += " " + coordonates[2];
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
    let newNr = moveNumber / 2;
    await getMovesHistory(newNr);
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
