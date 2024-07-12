package org.example.dao;

import java.util.List;

public interface ProjectUserDAO<T> {
    public T create(T t);
    public boolean delete(int id);
    public T getId(int id);
    public List<T> getAll();
}
