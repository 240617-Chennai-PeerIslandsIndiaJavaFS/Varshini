package org.example.dao;

import org.example.DBConnection;
import org.example.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeamMemberDAOImpl implements TeamMemberDAO {

    private static final Logger logger = LoggerFactory.getLogger(TeamMemberDAOImpl.class);
    static Connection con;

    public TeamMemberDAOImpl() {
        con = DBConnection.getConnection();
    }

    public boolean create(int team_id, int user_id) {
        String query = "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, team_id);
            pstmt.setInt(2, user_id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Created team member association for team_id {} and user_id {}", team_id, user_id);
                return true;
            } else {
                logger.warn("Failed to create team member association for team_id {} and user_id {}", team_id, user_id);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while creating team member association: {}", ex.getMessage());
        }

        return false;
    }

    @Override
    public List<Tasks> viewAssignedTasks(int userId) {
        String query = "SELECT * FROM tasks WHERE user_id = ?";
        List<Tasks> tasksList = new ArrayList<>();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, userId);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setProject_id(rs.getInt("project_id"));
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                task.setMilestone_id(rs.getInt("milestone_id"));
                task.setUser_id(rs.getInt("user_id"));
                task.setDescription(rs.getString("description"));

                tasksList.add(task);
            }
            if (!tasksList.isEmpty()) {
                logger.info("Fetched {} assigned tasks for user with ID {}", tasksList.size(), userId);
            } else {
                logger.warn("No assigned tasks found for user with ID {}", userId);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching assigned tasks for user with ID {}: {}", userId, ex.getMessage());
        }
        return tasksList;
    }

    @Override
    public Tasks viewTaskDetails(int taskId) {
        String query = "SELECT * FROM tasks WHERE task_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, taskId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setProject_id(rs.getInt("project_id"));
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                task.setMilestone_id(rs.getInt("milestone_id"));
                task.setUser_id(rs.getInt("user_id"));
                task.setDescription(rs.getString("description"));

                logger.debug("Fetched task details for task with ID {}", taskId);
                return task;
            } else {
                logger.warn("No task found with ID {}", taskId);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching task details for task with ID {}: {}", taskId, ex.getMessage());
        }
        return null;
    }

    public Tasks viewTaskDetails(int task_id, int user_id) {
        String query = "SELECT * FROM tasks WHERE task_id = ? AND user_id = ?";
        try (PreparedStatement ptmt = con.prepareStatement(query)) {
            ptmt.setInt(1, task_id);
            ptmt.setInt(2, user_id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setProject_id(rs.getInt("project_id"));
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                task.setMilestone_id(rs.getInt("milestone_id"));
                task.setDescription(rs.getString("description"));

                logger.debug("Fetched task details for task with ID {} assigned to user with ID {}", task_id, user_id);
                return task;
            } else {
                logger.warn("No task found with ID {} assigned to user with ID {}", task_id, user_id);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching task details for task with ID {} assigned to user with ID {}: {}", task_id, user_id, ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Tasks> viewProjectTasks(int projectId) {
        String query = "SELECT * FROM tasks WHERE project_id = ?";
        List<Tasks> tasksList = new ArrayList<>();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, projectId);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                Projects projects = new Projects();
                projects.setProject_id(rs.getInt("project_id"));
                task.setProjects(projects);
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                Milestones milestone = new Milestones();
                milestone.setMilestone_id(rs.getInt("milestone_id"));
                task.setMilestone(milestone);
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                task.setUser(user);
                task.setDescription(rs.getString("description"));

                tasksList.add(task);
            }
            if (!tasksList.isEmpty()) {
                logger.info("Fetched {} tasks for project with ID {}", tasksList.size(), projectId);
            } else {
                logger.warn("No tasks found for project with ID {}", projectId);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching tasks for project with ID {}: {}", projectId, ex.getMessage());
        }
        return tasksList;
    }

    @Override
    public List<Tasks> viewClientDetails(int clientId) {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        List<Tasks> clientDetails = new ArrayList<>();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, clientId);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Tasks client = new Tasks();
                client.setTask_id(rs.getInt("task_id"));
                Projects projects = new Projects();
                projects.setProject_id(rs.getInt("project_id"));
                client.setProjects(projects);
                client.setStart_date(rs.getDate("start_date"));
                client.setEnd_date(rs.getDate("end_date"));
                client.setTask_name(rs.getString("task_name"));
                client.setPercentage(rs.getDouble("percentage"));
                Milestones milestone = new Milestones();
                milestone.setMilestone_id(rs.getInt("milestone_id"));
                client.setMilestone(milestone);
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                client.setUser(user);
                client.setDescription(rs.getString("description"));

                clientDetails.add(client);
            }
            if (!clientDetails.isEmpty()) {
                logger.info("Fetched client details for client with ID {}", clientId);
            } else {
                logger.warn("No client details found for client with ID {}", clientId);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching client details for client with ID {}: {}", clientId, ex.getMessage());
        }
        return clientDetails;
    }

    public ResultSet viewClientDetail(int clientId) {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,clientId);
            ResultSet rs = preparedStatement.executeQuery();
            return  rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateTaskStatus(int taskId, Tasks task) {
        String query = "UPDATE tasks SET percentage = ?, milestone_id = ? WHERE task_id= ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setDouble(1, task.getPercentage());
            ptmt.setInt(2, task.getMilestone_id());
            ptmt.setInt(3, taskId);

            int rowsAffected = ptmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Updated task status for task with ID {}", taskId);
                return true;
            } else {
                logger.warn("No task found with ID {} to update status", taskId);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while updating task status for task with ID {}: {}", taskId, ex.getMessage());
        }
        return false;
    }
}
