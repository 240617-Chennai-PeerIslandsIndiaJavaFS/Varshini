package org.example.dao;

import java.util.List;

public interface UserDAO<T> {
    public T create(T t);
    public boolean delete(int userId);
    public boolean update(T t);
    public T getUserById(int id);
    public List<T> getAll();
}
