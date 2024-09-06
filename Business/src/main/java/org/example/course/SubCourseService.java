package org.example.course;

import com.example.models.courses.SubCourse;
import lombok.AllArgsConstructor;
import org.example.exceptions.SubCourseNotFoundException;
import org.example.repositories.course.SubCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("subCourseService")
@AllArgsConstructor
public class SubCourseService {

    private final SubCourseRepository subCourseRepository;

    public List<SubCourse> getAllSubCourses(String courseName) throws SubCourseNotFoundException {
        return subCourseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new SubCourseNotFoundException("there are no subCourses for this course"));
    }

    public List<SubCourse> getAllSubCourses() {
        return subCourseRepository.findAll();
    }


    public SubCourse getByName(String name, String courseName) throws SubCourseNotFoundException {
        return subCourseRepository.finByNameAndCourseName(name, courseName)
                .orElseThrow(() -> new SubCourseNotFoundException("there are no subCourses with that name"));
    }


    public void addSubCourse(SubCourse subCourse) {
        subCourseRepository.save(subCourse);
    }

    public void addAll(List<SubCourse> subCourseList) {
        subCourseRepository.saveAll(subCourseList);
    }
}
