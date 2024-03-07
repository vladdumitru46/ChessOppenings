let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let informationCourseValue = urlParams.get('courseTitle');
let informationCourseElement = document.getElementById("courseContent");
let pageTitle = document.getElementById("pageTitle");

if (informationCourseElement && informationCourseValue) {
    let courseData = JSON.parse(informationCourseValue);
    pageTitle.innerText = courseData.name;
    informationCourseElement.innerText = courseData.name + " \n\n\n\n" + courseData.description;
}


let boardUrl = "http://localhost:8080/chess/board/save";
console.log(boardUrl);

async function fetchData() {
    let boardUrl = "http://localhost:8080/chess/board/save";
    console.log(boardUrl);

    try {
        let r = await fetch(boardUrl, { method: "POST" });

        if (r.ok) {
            try {
                let id = await r.text();
                console.log('ID:', id);
                localStorage.setItem('boardId', id);
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

fetchData();

let square1 = null;
let square2 = null;

async function getPiece(square) {

    if (square1 == null) {
        square1 = square;
    } else {
        square2 = square;
    }
    var ok = 0;
    if (square1 != null && square2 != null && square1.getAttribute('data-piesa') !== "none") {

        if (square1.getAttribute('data-piesa').includes("king")) {
            const piesaSquare1 = square1.getAttribute('data-piesa');
            const imageUrlSquare1 = square1.querySelector('img').src;
            const springBootPort = 8080; // Replace with the actual port number
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `http://localhost:${springBootPort}/chess/move/castle`;
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
            ok = await castle(piesaSquare1, imageUrlSquare1, springBootURL, requestData, ok)
        }

        if (ok == 0) {
            console.log("de ce intrii aici?")
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

            var rookSquare = document.getElementById("00");
            var rookNewSquare = document.getElementById("03");
            rookNewSquare.setAttribute('data-piesa', rookSquare.getAttribute('data-piesa'));
            rookNewSquare.querySelector('img').src = rookSquare.querySelector('img').src;

            rookSquare.setAttribute('data-piesa', 'none');
            rookSquare.querySelector('img').src = "";

            square1 = null;
            square2 = null;
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

            square1 = null;
            square2 = null;
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

            square1 = null;
            square2 = null;
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
            square1 = null;
            square2 = null;
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
                        square1 = null;
                        square2 = null;

                        ok = 1;
                    }
                }
                console.log("am iesit de aici: " + ok)

            }
        }
        if (ok == 0) {
            const response1 = await fetch(springBootURL, requestData);

            if (response1.ok) {
                square2.setAttribute('data-piesa', piesaSquare1);
                square2.querySelector('img').src = imageUrlSquare1;

                square1.setAttribute('data-piesa', 'none');
                square1.querySelector('img').src = "";

                square1 = null;
                square2 = null;
                const response2 = await fetch(checkMateCheck, {
                    method: "POST",
                    body: JSON.stringify({
                        boardId: localStorage.getItem('boardId')
                    })
                });

                if (response2.ok) {
                    let res = await response2.text();
                    if (res !== "continue") {
                        window.alert(res);
                    }
                } else {
                    // handle other cases if needed
                }

                // await moveAi();
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

window.addEventListener('beforeunload', function () {

    let token = this.localStorage.getItem("token")
    if(token!=null){
    fetch('http://localhost:8080/chess/move/reset', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ /* your data here */ })
    })
        .then(response => {
            // handle the response from the server
        })
        .catch(error => {
            // handle any errors that occur during the request
        });
    }
    else{
        
        window.location.replace('../logIn/log-in.html');
    }
});

