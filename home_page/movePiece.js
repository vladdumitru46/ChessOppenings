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
    if (square1 != null && square2 != null && square1.getAttribute('data-piesa') !== "none") {
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

window.addEventListener('beforeunload', function() {
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



