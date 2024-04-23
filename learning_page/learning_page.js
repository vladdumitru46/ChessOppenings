// Definește funcția pentru a obține cursurile completate de la API
const baseUrl = "http://localhost:8080/chess";

async function getCompletedCourses(playerId) {
    const response = await fetch(`${baseUrl}/startCourse/getFinishedCourses?player=${player}`);
    const data = await response.json();
    return data;
}

// Funcția care aplică stilul verde pentru cursurile completate
async function colorCompletedCourses(playerId) {
    const completedCourses = await getCompletedCourses(playerId);
    const lis = document.querySelectorAll('.learningpage-ul li');
    
    lis.forEach(li => {
        const courseName = li.getAttribute('data-course').trim();
        if (completedCourses.includes(courseName)) {
            li.style.backgroundColor = 'green';
        }
    });
}

// Apelarea funcției pentru a colora cursurile completate
const player = localStorage.getItem("player");
colorCompletedCourses(player);


function openCourse(element) {
    let courseValue = element.getAttribute("data-course");
    const springBootPort = 8080; // Replace with the actual port number
    const springBootURL = `${baseUrl}/course/getCourse?name=${courseValue}`;
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