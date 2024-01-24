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


let boardUrl = "http://localhost:8080/board/save";
console.log(boardUrl);
// r = fetch(boardUrl, { method: "POST" })
// if (r.ok) {
//     try {
//         let id = r.text();
//         console.log('ID:', id);
//         localStorage.setItem('boardId', id);
//     } catch (error) {
//         console.error('Eroare la extragerea textului:', error);
//     }
// } else {
//     console.log('Răspuns incorect:', r.status);
// }
async function fetchData() {
    let boardUrl = "http://localhost:8080/board/save";
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

function getPiece(square) {

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
            const springBootURL = `http://localhost:${springBootPort}/move/castle`;
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

            if (square2.getAttribute("id") === "02") {
                fetch(springBootURL, requestData)
                    .then(response => {
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
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            }
            if (square2.getAttribute("id") === "06") {
                fetch(springBootURL, requestData)
                    .then(response => {
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
                        } else {
                            square1 = null;
                            square2 = null;
                            console.log("cannot move the piece there! check the backend logs for more information!")
                        }
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            }
            if (square2.getAttribute("id") === "72") {
                fetch(springBootURL, requestData)
                    .then(response => {
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
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            }
            if (square2.getAttribute("id") === "76") {
                fetch(springBootURL, requestData)
                    .then(response => {
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
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            }
        }
        if (ok == 0) {
            const piesaSquare1 = square1.getAttribute('data-piesa');
            const imageUrlSquare1 = square1.querySelector('img').src;
            const springBootPort = 8080; // Replace with the actual port number
            const atributes = piesaSquare1.split(' ');
            const springBootURL = `http://localhost:${springBootPort}/move/${atributes[1]}`;
            console.log(springBootURL);

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

            let checkMateCheck = "http://localhost:8080/move/checkmate"
            makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, atributes[0])
        }
    }
}


async function makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, colour) {
    try {

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

            let ai = "http://localhost:8080/move/ai/makeMove";
            console.log(ai);
            response3 = await fetch(ai, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    boardId: localStorage.getItem('boardId')
                })
            })
            if (response3.ok) {
                let bestMove = await response3.text();

                let coordonates = bestMove.split(" ")
                let coordonatesSquare1 = coordonates[0]
                let coordonatesSquare2 = coordonates[1]

                console.log(coordonatesSquare1 + " " + coordonatesSquare2);

                square1 = document.getElementById(coordonatesSquare1)
                square2 = document.getElementById(coordonatesSquare2)

                imageUrlSquare1 = square1.querySelector('img').src;
                square2.setAttribute('data-piesa', piesaSquare1);
                square2.querySelector('img').src = imageUrlSquare1;

                square1.setAttribute('data-piesa', 'none');
                square1.querySelector('img').src = "";

                square1 = null;
                square2 = null;
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
                    window.alert("Check mate!");
                } else {
                    // handle other cases if needed
                }

            } else {
                // Handle other cases if needed
            }
        } else {
            square1 = null;
            square2 = null;
            console.log("cannot move the piece there! check the backend logs for more information!");
        }



    } catch (error) {
        console.error("Error:", error);
    }
}

window.addEventListener('beforeunload', function () {
    fetch('http://localhost:8080/move/reset', {
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
});


