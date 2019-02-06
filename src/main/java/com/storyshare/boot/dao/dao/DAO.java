package com.storyshare.boot.dao.dao;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.io.Serializable;

public interface DAO<T> {
    T save(T t);

    T get(Serializable t);

    T load(Serializable t);

    T update(T t);

    void delete(T t);

    EntityManager getCurrentEntityManager();

    Session getCurrentSession();
}
