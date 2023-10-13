let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let informationCourseValue = urlParams.get('informationCourse');

let informationCourseElement = document.getElementById("informationCourse");

if (informationCourseElement && informationCourseValue) {
    informationCourseElement.value = informationCourseValue;
}

function startCourse(course){
    window.location.href = '../home_page/homePage.html?courseTitle=' + encodeURIComponent(informationCourseValue);
}