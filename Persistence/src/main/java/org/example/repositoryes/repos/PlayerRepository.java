package org.example.repositoryes.repos;

import com.example.models.courses.Player;
import org.example.repositoryes.interfaces.IRepoPlayer;
import org.example.repositoryes.utils.Factory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerRepository implements IRepoPlayer {
    @Override
    public Player add(Player entity) {
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
    public Player delete(Integer integer) {
        Player player = findOne(integer);
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Player criteria = session.createQuery("from Player where id = :entity", Player.class).setParameter("entity", integer).setMaxResults(1).uniqueResult();
                session.delete(criteria);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                    return null;
                }
            }
        }
        return player;
    }

    @Override
    public Player update(Player entity) {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Player player = session.load(Player.class, entity.getId());
                player.setName(entity.getName());
                player.setEmail(entity.getEmail());
                player.setPassword(entity.getPassword());
                player.setUserName(entity.getUserName());
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
    public Player findOne(Integer integer) {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Player player = session.createQuery("FROM Player WHERE id=:id", Player.class).
                        setParameter("id", integer).setMaxResults(1).uniqueResult();
                transaction.commit();
                return player;
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<Player> findAll() {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Player> list = session.createQuery("FROM Player ", Player.class).stream().toList();
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

    @Override
    public Player findByEmailAndPassword(String email, String password) {
        try (Session session = Factory.getProperties()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Player employee = session.createQuery("from Player where email = :email and password= :password", Player.class).
                        setParameter("email", email).setParameter("password", password).setMaxResults(1).uniqueResult();
                transaction.commit();
                return employee;
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }
}
