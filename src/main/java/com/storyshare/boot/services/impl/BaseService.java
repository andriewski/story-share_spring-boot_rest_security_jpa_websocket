package com.storyshare.boot.services.impl;

import com.storyshare.boot.dao.dao.DAO;
import com.storyshare.boot.services.IService;
import com.storyshare.boot.services.ServiceException;
import lombok.Data;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
@Data
public abstract class BaseService<T> implements IService<T> {
    @Autowired
    private DAO<T> dao;

    @Override
    public T save(T t) {
        try {
            return dao.save(t);
        } catch (HibernateException e) {
            throw new ServiceException("Error saving " + t);
        }
    }

    @Override
    public T get(Serializable t) {
        try {
            return dao.get(t);
        } catch (HibernateException e) {
            throw new ServiceException("Error getting " + t);
        }
    }

    @Override
    public T load(Serializable t) {
        try {
            return dao.load(t);
        } catch (HibernateException e) {
            throw new ServiceException("Error loading " + t);
        }
    }

    @Override
    public T update(T t) {
        try {
            return dao.update(t);
        } catch (HibernateException e) {
            throw new ServiceException("Error updating " + t);
        }
    }

    @Override
    public void delete(T t) {
        try {
            dao.delete(t);
        } catch (HibernateException e) {
            throw new ServiceException("Error deleting " + t);
        }
    }
}
