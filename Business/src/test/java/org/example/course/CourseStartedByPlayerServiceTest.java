package org.example.course;

import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import org.example.repositories.course.CourseStartedByPlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseStartedByPlayerServiceTest {

    @Mock
    private CourseStartedByPlayerRepository courseStartedByPlayerRepository;

    private CourseStartedByPlayerService courseStartedByPlayerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseStartedByPlayerService = new CourseStartedByPlayerService(courseStartedByPlayerRepository);
    }

    @Test
    void addPlayerThatStartedTheCourse() {
        CourseStartedByPlayer courseStartedByPlayer = new CourseStartedByPlayer();
        courseStartedByPlayerService.addPlayerThatStartedTheCourse(courseStartedByPlayer);
        assertEquals(CourseStatus.InPROGRESS, courseStartedByPlayer.getCourseStatus());
        verify(courseStartedByPlayerRepository, times(1)).save(courseStartedByPlayer);
    }

    @Test
    void finishCourse() {
        CourseStartedByPlayer courseStartedByPlayer = new CourseStartedByPlayer();
        courseStartedByPlayerService.finishCourse(courseStartedByPlayer);
        assertEquals(CourseStatus.COMPLETED, courseStartedByPlayer.getCourseStatus());
    }

    @Test
    void getCourseStartedByPlayerAfterPlayerIdAndCourseName() throws Exception {
        Integer playerId = 1;
        String courseName = "Course 1";
        Integer boardId = 1;
        CourseStartedByPlayer expectedCourseStartedByPlayer = new CourseStartedByPlayer();
        when(courseStartedByPlayerRepository.findByPlayerIdAndCourseNameAndBoardId(playerId, courseName, boardId))
                .thenReturn(Optional.of(expectedCourseStartedByPlayer));

        CourseStartedByPlayer actualCourseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(playerId, courseName, boardId);

        assertEquals(expectedCourseStartedByPlayer, actualCourseStartedByPlayer);
    }

    @Test
    void getCourseStartedByPlayerAfterPlayerIdAndCourseName_courseDoesNotExist() {
        Integer playerId = 1;
        String courseName = "Course 1";
        Integer boardId = 1;
        when(courseStartedByPlayerRepository.findByPlayerIdAndCourseNameAndBoardId(playerId, courseName, boardId))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(playerId, courseName, boardId);
        });
    }

    @Test
    void update() {
        CourseStartedByPlayer courseStartedByPlayer = new CourseStartedByPlayer();
        courseStartedByPlayer.setMoveNumber(5);
        courseStartedByPlayer.setCourseStatus(CourseStatus.InPROGRESS);
        courseStartedByPlayer.setWhitesTurn(true);

        courseStartedByPlayerService.update(courseStartedByPlayer);

        assertEquals(5, courseStartedByPlayer.getMoveNumber());
        assertEquals(CourseStatus.InPROGRESS, courseStartedByPlayer.getCourseStatus());
        assertTrue(courseStartedByPlayer.isWhitesTurn());
    }
}