package com.nttdata.service;

import java.util.List;

public interface AbstractService<T> {

    List<T> findAll();
    T create(T t);
    T update(T t);
    void delete(T t);
}
