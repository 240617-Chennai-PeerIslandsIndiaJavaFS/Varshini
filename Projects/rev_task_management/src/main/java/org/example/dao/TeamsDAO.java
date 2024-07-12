package org.example.dao;

import org.example.model.Teams;

import java.util.List;

public interface TeamsDAO {
    public Teams create(Teams t);
    public boolean delete(int id);
    public boolean update(String client_name, int id);
    public Teams getId(int id);
    public List<Teams> getAll();
}
