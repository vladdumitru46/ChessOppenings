const baseUrl = "http://localhost:8080/chess";

const token = localStorage.getItem("token");

colorCompletedCourses(token);
async function getCompletedCourses() {
    const response = await fetch(`${baseUrl}/startCourse/getFinishedCourses?token=${token}`);
    return await response.json();
}

async function colorCompletedCourses(token) {
    const completedCourses = await getCompletedCourses(token);
    const lis = document.querySelectorAll('.learningpage-ul li');

    lis.forEach(li => {
        const courseName = li.getAttribute('data-course').trim();
        if (completedCourses.includes(courseName)) {
            li.style.backgroundColor = 'green';
        }
    });
}


function openCourse(element) {
    let courseValue = element.getAttribute("data-course");
    const springBootURL = `${baseUrl}/course/getCourse?name=${courseValue}`;
    let course = {};

    const requestData = {
        method: "GET",
    };

    fetch(springBootURL, requestData)
        .then(response => {
            if (response.ok) {
                return response.json();
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
    window.location.href = "../choseColour/chose-black-or-white.html";
});
document.getElementById('gameHistoryLink').addEventListener('click', function () {
    window.location.href = "../history_page/historypage.html";
});

function logOut() {
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}

function viewProfile() {
    window.location.replace('../viewProfile/profile-page.html');
}

let coll = document.getElementsByClassName("collapsible");
let i;

for (i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function () {
        this.classList.toggle("active");
        let content = this.nextElementSibling;
        if (content.style.display === "block") {
            content.style.display = "none";
        } else {
            content.style.display = "block";
        }
    });
}