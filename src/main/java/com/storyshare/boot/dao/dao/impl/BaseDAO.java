package com.storyshare.boot.dao.dao.impl;

import com.storyshare.boot.dao.dao.DAO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Slf4j
public class BaseDAO<T> implements DAO<T> {
    @PersistenceContext
    private EntityManager em;
    @SuppressWarnings("unchecked")
    private final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public T save(T t) {
        em.persist(t);
        log.info("Save: " + t);

        return t;
    }

    @Override
    public T get(Serializable t) {
        log.info("Get: " + t);

        return (T) em.find(entityClass, t);
    }

    @Override
    public T update(T t) {
        log.info("Update: " + t);

        return em.merge(t);
    }

    @Override
    public T load(Serializable t) {
        log.info("Load: " + t);

        return (T) getCurrentSession().load(entityClass, t);
    }

    @Override
        public void delete(T t) {
        log.info("Delete: " + t);
        em.remove(t);
    }

    public EntityManager getCurrentEntityManager() {
        return em;
    }

    public Session getCurrentSession() {
        return em.unwrap(Session.class);
    }
}
