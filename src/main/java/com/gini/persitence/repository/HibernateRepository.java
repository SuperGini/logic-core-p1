package com.gini.persitence.repository;

/**
 * https://vladmihalcea.com/best-spring-data-jparepository/
 * */


public interface HibernateRepository<T> {

    @Deprecated(since = " is a shit method use -> persist")
    <S extends T> S save(S entity);

    <S extends T> S persist(S entity);


}
