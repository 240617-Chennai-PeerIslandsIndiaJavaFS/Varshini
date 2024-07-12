package org.example.dao;

import org.example.model.Clients;
import org.example.model.Tasks;

import java.util.List;

public interface ProjectManagerDAO {
    boolean updateClient(int clientId, Clients newDetails);
    boolean deleteClient(int clientId);
    Clients viewClientDetails(int clientId);

    boolean addTeamMemberToProject(int projectId, int userId);
    boolean removeTeamMemberFromProject(int projectId, int userId);

    Tasks assignTaskToTeamMember(Tasks task, int userId);
    boolean updateTask(int taskId, Tasks newDetails);
    boolean deleteTask(int projectId);
    List<Tasks> viewTaskDetails(int taskId);
}
