package org.example.course;

import com.example.models.courses.Course;
import com.example.models.courses.SubCourse;
import org.example.repositoryes.course.SubCourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SubCourseServiceTest {

    @Mock
    private SubCourseRepository subCourseRepository;

    private SubCourseService subCourseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subCourseService = new SubCourseService(subCourseRepository);
    }

    @Test
    void testGetAllSubCourses() throws Exception {
        String courseName = "Math";
        Course course = new Course();
        List<SubCourse> subCourses = new ArrayList<>();
        subCourses.add(new SubCourse("SubCourse 1", "", "", course));
        subCourses.add(new SubCourse("SubCourse 2", "", "", course));

        when(subCourseRepository.findByCourseName(courseName)).thenReturn(Optional.of(subCourses));

        List<SubCourse> result = subCourseService.getAllSubCourses(courseName);

        assertEquals(subCourses, result);
        verify(subCourseRepository, times(1)).findByCourseName(courseName);
    }

    @Test
    void testGetAllSubCourses_NoSubCourses() {
        String courseName = "Math";

        when(subCourseRepository.findByCourseName(courseName)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> subCourseService.getAllSubCourses(courseName));
        verify(subCourseRepository, times(1)).findByCourseName(courseName);
    }

    @Test
    void testGetByName() throws Exception {
        String name = "SubCourse 1";
        String courseName = "Math";
        Course course = new Course();
        SubCourse subCourse = new SubCourse(name, "", "", course);

        when(subCourseRepository.finByNameAndCourseName(name, courseName)).thenReturn(Optional.of(subCourse));

        SubCourse result = subCourseService.getByName(name, courseName);

        assertEquals(subCourse, result);
        verify(subCourseRepository, times(1)).finByNameAndCourseName(name, courseName);
    }

    @Test
    void testGetByName_SubCourseNotFound() {
        String name = "SubCourse 1";
        String courseName = "Math";

        when(subCourseRepository.finByNameAndCourseName(name, courseName)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> subCourseService.getByName(name, courseName));
        verify(subCourseRepository, times(1)).finByNameAndCourseName(name, courseName);
    }
}
