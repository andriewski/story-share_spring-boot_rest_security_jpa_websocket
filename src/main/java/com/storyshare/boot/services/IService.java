package com.storyshare.boot.services;

import java.io.Serializable;

public interface IService<T> {
    T save(T t);

    T get(Serializable t);

    T load(Serializable t);

    T update(T t);

    void delete(T t);
}
