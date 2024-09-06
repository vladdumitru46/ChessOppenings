package org.example.course;

import com.example.models.courses.Course;
import org.example.repositories.course.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        courseService = new CourseService(courseRepository);
    }

    @Test
    void testFindCourseById() {
        // Arrange
        int courseId = 1;
        Course expectedCourse = new Course( "Math", "description");
        expectedCourse.setId((long) courseId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(expectedCourse));

        // Act
        Optional<Course> actualCourse = courseService.findCourseById(courseId);

        // Assert
        Assertions.assertEquals(expectedCourse, actualCourse.orElse(null));
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testFindCourseByName() throws Exception {
        // Arrange
        String courseName = "Math";
        Course expectedCourse = new Course( courseName,"description");

        when(courseRepository.findCourseByName(courseName)).thenReturn(Optional.of(expectedCourse));

        // Act
        Course actualCourse = courseService.findCourseByName(courseName);

        // Assert
        Assertions.assertEquals(expectedCourse, actualCourse);
        verify(courseRepository, times(1)).findCourseByName(courseName);
    }
}