package org.example.dao;

import org.example.model.Clients;
import org.example.model.Projects;
import org.example.model.Role;
import org.example.model.Users;

import java.sql.ResultSet;

public interface AdminDAO {
    Users createUser(Users users);
    boolean updateUser(Users users);
    boolean deactivateUser(int userId);
    boolean assignRole(int userId, Role newRole);
    Clients createClient(Clients clients);
    Projects createProject(Projects projects);
    ResultSet trackUserActivity(int userId);
    ResultSet monitorTaskCompletion(int taskid);
}
