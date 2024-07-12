package org.example.dao;

import org.example.DBConnection;
import org.example.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerDAOImpl implements ProjectManagerDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProjectManagerDAOImpl.class);
    static Connection con;

    public ProjectManagerDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public boolean updateClient(int clientId, Clients newDetails) {
        String query = "UPDATE clients SET client_name = ?, client_email = ?, client_phone = ? WHERE client_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, newDetails.getClient_name());
            ptmt.setString(2, newDetails.getEmail());
            ptmt.setString(3, newDetails.getPhoneNumber());
            ptmt.setInt(4, clientId);
            int rowsAffected = ptmt.executeUpdate();
            logger.info("{} client details updated successfully", rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteClient(int clientId) {
        String query = "DELETE FROM clients WHERE client_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, clientId);
            int rowsAffected = ptmt.executeUpdate();
            logger.info("{} client(s) deleted", rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public Clients viewClientDetails(int clientId) {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, clientId);
            ResultSet rs = ptmt.executeQuery();
            Clients clients = new Clients();

            while (rs.next()) {
                clients.setClient_id(rs.getInt(1));
                clients.setClient_name(rs.getString(2));
                clients.setClient_company_name(rs.getString(3));
                clients.setEmail(rs.getString(4));
                clients.setPhoneNumber(rs.getString(5));
            }
            logger.info("Client details retrieved for ID: {}", clientId);
            return clients;

        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean addTeamMemberToProject(int projectId, int userId) {
        try {
            String query = "INSERT INTO project_users (project_id, user_id) VALUES (?, ?)";
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, projectId);
            ptmt.setInt(2, userId);
            int rowsAffected = ptmt.executeUpdate();
            logger.info("Added user {} to project {}", userId, projectId);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeTeamMemberFromProject(int projectId, int userId) {
        String query = "DELETE FROM project_users WHERE project_id = ? AND user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, projectId);
            ptmt.setInt(2, userId);
            int rowsAffected = ptmt.executeUpdate();
            logger.info("Removed user {} from project {}", userId, projectId);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public Tasks assignTaskToTeamMember(Tasks task, int userId) {
        String query = "INSERT INTO tasks (project_id, start_date, end_date, task_name,milestone_id, user_id, description) VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, task.getProject_id());
            ptmt.setDate(2, task.getStart_date());
            ptmt.setDate(3, task.getEnd_date());
            ptmt.setString(4, task.getTask_name());
            ptmt.setInt(5,task.getMilestone_id());
            ptmt.setInt(6, task.getUser_id());
            ptmt.setString(7, task.getDescription());
            int rowsAffected = ptmt.executeUpdate();
            logger.info("Assigned task '{}' to user {}", task.getTask_name(), userId);
            return rowsAffected > 0 ? task : null;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return null;
    }

    public boolean assignTaskToTeamMemberDummy(Tasks task) {
        String query = "INSERT INTO tasks (project_id, start_date, end_date, task_name, user_id, description) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, task.getProject_id());
            ptmt.setDate(2, task.getStart_date());
            ptmt.setDate(3, task.getEnd_date());
            ptmt.setString(4, task.getTask_name());
            ptmt.setInt(5, task.getUser_id());
            ptmt.setString(6, task.getDescription());
            int rowsAffected = ptmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateTask(int projectId, Tasks newDetails) {
        String query = "UPDATE tasks SET percentage = ?, milestone_id = ? WHERE project_id= ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setDouble(1, newDetails.getPercentage());
            ptmt.setInt(2, newDetails.getMilestone_id());
            ptmt.setInt(3, projectId);

            int rowsAffected = ptmt.executeUpdate();
            logger.info("{} task(s) updated for project {}", rowsAffected, projectId);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return false;
    }
    public boolean updateTask1(int task_id,Date date) {
        String query = "UPDATE tasks SET end_date=? where task_id=?";
        try {
            PreparedStatement pt = con.prepareStatement(query);
            pt.setDate(1,date);
            pt.setInt(2,task_id);
            int row = pt.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } public boolean updateTaskBasedUserId(int task_id,int userId) {
        String query = "UPDATE tasks SET user_id=? where task_id=?";
        try {
            PreparedStatement pt = con.prepareStatement(query);
            pt.setInt(1,userId);
            pt.setInt(2,task_id);
            int row = pt.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Tasks> getTaskBasedProjectId(int projectId) {
        String query = "SELECT * FROM tasks WHERE project_id = ?";
        List<Tasks> taskDe = new ArrayList<>();

        try (PreparedStatement ptmt = con.prepareStatement(query)) {
            ptmt.setInt(1, projectId);
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
                task.setDescription(rs.getString("description"));
                taskDe.add(task);
            }

        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }

        return taskDe;
    }

    @Override
    public boolean deleteTask(int projectId) {
        String query = "DELETE FROM tasks WHERE project_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, projectId);
            int rowsAffected = ptmt.executeUpdate();
            logger.info("{} task(s) deleted for project {}", rowsAffected, projectId);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public List<Tasks> viewTaskDetails(int projectId) {
        String query = "SELECT * FROM tasks WHERE project_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, projectId);
            ResultSet rs = ptmt.executeQuery();
            List<Tasks> tasks = new ArrayList<>();

            while (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt(1));
                task.setProject_id(rs.getInt(2));
                task.setStart_date(rs.getDate(3));
                task.setEnd_date(rs.getDate(4));
                task.setTask_name(rs.getString(5));
                task.setPercentage(rs.getDouble(6));
                task.setMilestone_id(rs.getInt(7));
                task.setUser_id(rs.getInt(8));
                task.setDescription(rs.getString(9));
                task.setCreatedAt(rs.getTimestamp(10));
                task.setUpdatedAt(rs.getTimestamp(11));
                tasks.add(task);
            }
            logger.info("Retrieved {} task(s) for project {}", tasks.size(), projectId);
            return tasks;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return null;
    }
    public boolean resetPassword(String email,String password){
        String query = "UPDATE users set password =? where email=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,password);
            preparedStatement.setString(2,email);
            int row = preparedStatement.executeUpdate();
            return row>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
