package org.example.dao;

import org.example.model.Tasks;
import org.example.model.Teams;
import org.example.model.Users;

import java.sql.ResultSet;
import java.util.List;

public interface TeamMemberDAO {

    List<Tasks> viewAssignedTasks(int userId);

    Tasks viewTaskDetails(int taskId);
    List<Tasks> viewProjectTasks(int projectId);
    List<Tasks> viewClientDetails(int clientId);

    boolean updateTaskStatus(int taskId, Tasks task);
}
