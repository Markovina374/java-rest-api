package com.cvtmarkov.javarestapi.repository;

import java.util.List;

public interface CrudRepository<T> {

    List<T> findAll();

    T findById(long id);

    String deleteById(long id);

    T save(T object);

    T update(long id, T object);

}
