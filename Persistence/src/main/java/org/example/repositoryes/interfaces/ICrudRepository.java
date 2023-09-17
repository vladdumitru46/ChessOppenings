package org.example.repositoryes.interfaces;

import com.example.models.courses.Entity;

public interface ICrudRepository<ID, E extends Entity<ID>> {
    E add(E entity);

    E delete(ID id);

    E update(E entity);

    E findOne(ID id);

    Iterable<E> findAll();
}
