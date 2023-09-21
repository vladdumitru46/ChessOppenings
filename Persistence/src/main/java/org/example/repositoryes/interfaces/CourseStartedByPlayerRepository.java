package org.example.repositoryes.interfaces;

import com.example.models.courses.CourseStartedByPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStartedByPlayerRepository extends JpaRepository<CourseStartedByPlayer, Integer> {
}
