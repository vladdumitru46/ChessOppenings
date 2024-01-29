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
r = fetch(boardUrl, { method: "POST" })
if (r.ok) {
    let id = r.text();

    localStorage.setItem('boardId', id);
}

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
                const springBootURL = `http://localhost:${springBootPort}/chess/move/castle`;
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
                const springBootURL = `http://localhost:${springBootPort}/chess/move/${atributes[1]}`;
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

                let checkMateCheck = "http://localhost:8080/chess/move/checkmate"
                makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, atributes[0])
            }
        }
    }


    async function makeMoves(springBootURL, imageUrlSquare1, requestData, piesaSquare1, checkMateCheck, colour) {
        try {
            bestMove = "";
            let ai = "http://localhost:8080/chess/move/ai/bestMove";
            console.log(ai);
            response3 = await fetch(ai, {
                method: "POST",
                body: JSON.stringify({
                    isWhite: colour,
                    boardId: localStorage.getItem('boardId')
                })
            })
            if (response3.ok) {
                bestMove = await response3.text();
                console.log(bestMove);
            } else {
                // Handle other cases if needed
            }
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
                window.alert("best move for " + colour + " is: " + bestMove);
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
        fetch('http://localhost:8080/chessmove/reset', {
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

