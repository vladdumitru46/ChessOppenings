package org.example.repositoryes.repos;

import com.example.models.courses.Course;
import org.example.repositoryes.interfaces.ICrudRepository;
import org.example.repositoryes.utils.Factory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CourseRepository implements ICrudRepository<Integer, Course> {
    @Override
    public Course add(Course entity) {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(entity);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return entity;
    }

    @Override
    public Course delete(Integer integer) {
        Course course = findOne(integer);
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Course criteria = session.createQuery("from Course where id = :entity", Course.class).setParameter("entity", integer).setMaxResults(1).uniqueResult();
                session.delete(criteria);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                    return null;
                }
            }
        }
        return course;
    }

    @Override
    public Course update(Course entity) {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Course course = session.load(Course.class, entity.getId());
                course.setName(entity.getName());
                course.setDescription(entity.getDescription());
                course.setMovesThatTheComputerWillPlay(entity.getMovesThatTheComputerWillPlay());
                course.setMovesThatThePlayerShouldPlay(entity.getMovesThatThePlayerShouldPlay());
                course.setPlayerId(entity.getPlayerId());
                course.setCourseStatus(entity.getCourseStatus());
                course.setBoard(entity.getBoard());
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                    return null;
                }
            }
        }
        return entity;
    }

    @Override
    public Course findOne(Integer integer) {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Course course = session.createQuery("FROM Course WHERE id=:id", Course.class).
                        setParameter("id", integer).setMaxResults(1).uniqueResult();
                transaction.commit();
                return course;
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<Course> findAll() {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Course> list = session.createQuery("FROM Course", Course.class).stream().toList();
                transaction.commit();
                return list;
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                    return null;
                }
            }
        }
        return null;
    }
}
