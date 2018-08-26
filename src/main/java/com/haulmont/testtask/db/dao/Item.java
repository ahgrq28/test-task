package com.haulmont.testtask.db.dao;

import com.haulmont.testtask.db.models.Model;

import java.util.List;

/**
 * common between interfaces
 * @author Kabanov Andrey
 */
public interface Item<T extends Model> {

    public T getById(Long id);
    public List<T> getAll();
    public T add(T model);
    public boolean update(T model);
    public boolean delete(T model);
}
