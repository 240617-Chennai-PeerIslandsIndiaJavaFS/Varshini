package org.example.dao;

import java.util.List;

public interface TimeStampDAO<T> {
    public boolean create(int team_id);
    public boolean delete(int id);
    boolean update(int taskId,int milestoneId);
    public T getId(int id);
    public List<T> getAll();
}
