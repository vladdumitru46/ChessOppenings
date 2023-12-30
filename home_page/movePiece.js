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
                    start: square1.getAttribute("id"),
                    end: square2.getAttribute("id"),
                    pieceColour: atributes[0]
                })

            };
            // alert(square1.getAttribute("data-piesa") + "\n" + square2.getAttribute("data-piesa"))
            // Send the POST request to the correct URL
            fetch(springBootURL, requestData)
                .then(response => {
                    if (response.ok) {
                        square2.setAttribute('data-piesa', piesaSquare1);
                        square2.querySelector('img').src = imageUrlSquare1;

                        square1.setAttribute('data-piesa', 'none');
                        square1.querySelector('img').src = "";

                        square1 = null;
                        square2 = null;
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
}

window.addEventListener('beforeunload', function () {
    fetch('http://localhost:8080/move/reset', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ /* your data here */})
    })
        .then(response => {
            // handle the response from the server
        })
        .catch(error => {
            // handle any errors that occur during the request
        });
});



