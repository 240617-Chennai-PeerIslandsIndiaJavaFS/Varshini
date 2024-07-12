package org.example.dao;

import java.util.List;

public interface ClientDAO<T> {
    public T create(T t);
    public boolean delete(int id);
    public boolean update(String client_name, int id);
    public T getId(int id);
    public List<T> getAll();
}
