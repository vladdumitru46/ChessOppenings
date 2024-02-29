package org.example.course;

import com.example.models.courses.SubCourse;
import lombok.AllArgsConstructor;
import org.example.repositoryes.course.SubCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("subCourseService")
@AllArgsConstructor
public class SubCourseService {

    private final SubCourseRepository subCourseRepository;

    public List<SubCourse> getAllSubCourses(String courseName) throws Exception {
        Optional<List<SubCourse>> subCourses = subCourseRepository.findByCourseName(courseName);
        if (subCourses.isEmpty()) {
            throw new Exception("there are no subCourses for this course");
        }
        return subCourses.get();
    }


    public SubCourse getByName(String name, String courseName) throws Exception {
        Optional<SubCourse> subCourse = subCourseRepository.finByNameAndCourseName(name, courseName);
        if (subCourse.isEmpty()) {
            throw new Exception("there are no subCourses with that name");
        }
        return subCourse.get();
    }
}
