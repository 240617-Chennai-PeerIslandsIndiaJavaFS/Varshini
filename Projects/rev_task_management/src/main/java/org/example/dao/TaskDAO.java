package org.example.dao;

import java.util.List;

public interface TaskDAO<T> {
    public T create(T t);
    public boolean delete(int id);
    public boolean update(T t);
    public T getId(int id);
    public List<T> getAll();
}
