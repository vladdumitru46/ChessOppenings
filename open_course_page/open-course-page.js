let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let informationCourseValue = urlParams.get('informationCourse');
let informationCourseElement = document.getElementById("informationCourse");
let pageTitle = document.getElementById("pageTitle");

if (informationCourseElement && informationCourseValue) {
    let courseData = JSON.parse(informationCourseValue);
    pageTitle.innerText = courseData.name;
    informationCourseElement.value = courseData.name + " \n\n\n\n" + courseData.description;
}

function startCourse() {
    let queryString = window.location.search;
    let urlParams = new URLSearchParams(queryString);
    let informationCourseValue1 = urlParams.get('informationCourse');

    window.alert(informationCourseValue1)
    window.location.href = '../home_page/homePage.html?courseTitle=' + informationCourseValue1;
}

