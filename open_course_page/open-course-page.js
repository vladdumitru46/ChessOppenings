let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let informationCourseValue = urlParams.get('informationCourse');
let informationCourseElement = document.getElementById("informationCourse");
let pageTitle = document.getElementById("pageTitle");

if (informationCourseElement && informationCourseValue) {
    let courseData = JSON.parse(informationCourseValue);
    console.log(courseData)
    pageTitle.innerText = courseData.name;
    informationCourseElement.innerHTML = `${courseData.name}<br><br>${courseData.description}<br><br>` +
    `<iframe src="${courseData.video}" width="40%" height="30%" allow="autoplay"></iframe>`;

//         `<iframe title='YouTube video player' type=\"text/html\" width='640'  
// height='390' src='https://www.youtube.com/embed/JVxENCPcCjU' frameborder='0' 
//  allowFullScreen></iframe>`

}




async function startCourse() {
    console.log("BAHDUD")
    let url = "http://localhost:8080/chess/startCourse/start"
    let playerUsername = localStorage.getItem("player");
    let response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            courseName: pageTitle.innerText,
            playerUsername: playerUsername
        })
    })
    if (response.ok) {
        let resp = await response.text();
        localStorage.setItem("boardId", resp)
        window.location.href = '../chessBoard/chessBoardFourCourse.html?courseTitle=' + pageTitle.innerText;

    } else {
        let res = await response.text();
        window.alert(res);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    let token = localStorage.getItem("token");
    if (token === null) {
        window.location.replace('../logIn/log-in.html');
    }
});

document.getElementById('homeLink').addEventListener('click', function () {
    window.location.href = '../home_page/home-page.html?newGame=yes';
});

document.getElementById('playAgainstAiLink').addEventListener('click', function () {
    window.location.href = '../chessBoard/chessBoard.html';
});


function logOut(){
    localStorage.clear();
    window.location.replace('../logIn/log-in.html');
}