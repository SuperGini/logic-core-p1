package com.gini.persitence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


public class HibernateRepositoryImpl<T> implements HibernateRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <S extends T> S save(S entity) {
        throw new UnsupportedOperationException("This method is burning the CPU pls use the persis method!!;)");
    }

    @Override
    public <S extends T> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

}
