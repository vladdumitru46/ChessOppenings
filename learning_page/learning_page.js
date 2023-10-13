function openCourse(element) {
    let courseValue = element.getAttribute("data-course");
    window.location.href = '../open_course_page/oppen-course-page.html?informationCourse=' + encodeURIComponent(courseValue);
}