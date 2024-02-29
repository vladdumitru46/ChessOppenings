function openCourse(element) {
    let courseValue = element.getAttribute("data-course");
    const springBootPort = 8080; // Replace with the actual port number
    const springBootURL = `http://localhost:${springBootPort}/chess/course/getCourse?name=${courseValue}`;
    let course = {}; // Create an empty object to store the course data

    // Construct the request object
    const requestData = {
        method: "GET",
    };

    // Send the GET request to the correct URL
    fetch(springBootURL, requestData)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse the response as JSON
            } else {
                return response.text().then(errorData => {
                    throw new Error(errorData);
                });
            }
        })
        .then(courseData => {
            course = courseData; // Save the course data in the object
            console.log(course);
            window.location.href = '../open_course_page/open-course-page.html?informationCourse=' + encodeURIComponent(JSON.stringify(course));
        })
        .catch(error => {
            console.error("Error:", error.message);
            window.alert(error.message);
        });
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
    window.location.href = '../chessBoard/chessBoard.html?newGame=yes';
});
function logOut(){
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}