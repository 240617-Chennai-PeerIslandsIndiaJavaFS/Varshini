package org.example.service;

import org.example.dao.MilestoneDAOImp;
import org.example.dao.ProjectDAOImp;
import org.example.dao.TaskDAOImp;
import org.example.dao.TimeStampDAOImp;
import org.example.model.Clients;
import org.example.model.Projects;
import org.example.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectManagerService1 {
    private static final Logger logger = LoggerFactory.getLogger(ProjectManagerService.class);
    private ProjectDAOImp projectDAOImp;
    private Connection con;
    private TaskDAOImp taskDAOImp;

    public ProjectManagerService1(ProjectDAOImp projectDAOImp, Connection con) {
        this.projectDAOImp = projectDAOImp;
        this.con = con;
    }

    public ProjectManagerService1(ProjectDAOImp projectDAOImp, TimeStampDAOImp timeStampDAOImp, MilestoneDAOImp milestoneDAOImp, Scanner sc) {
    }

    public Clients viewProjectClientDetails(int projectId) {
        Projects project = projectDAOImp.getId(projectId);
        if (project != null) {
            logger.info("Retrieving client details for project ID {}", projectId);
            return viewClientDetails(project.getClient_id());
        } else {
            logger.warn("Project with ID {} not found", projectId);
            return null;
        }
    }

    public Clients viewClientDetails(int clientId) {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        try (PreparedStatement ptmt = con.prepareStatement(query)) {
            ptmt.setInt(1, clientId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Clients client = new Clients();
                client.setClient_id(rs.getInt(1));
                client.setClient_name(rs.getString(2));
                client.setClient_company_name(rs.getString(3));
                client.setEmail(rs.getString(4));
                client.setPhoneNumber(rs.getString(5));
                logger.info("Client details retrieved for ID: {}", clientId);
                return client;
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return null;
    }
    public boolean deleteTask(int projectId) {
        String query = "DELETE FROM tasks WHERE project_id = ?";
        try (PreparedStatement ptmt = con.prepareStatement(query)) {
            ptmt.setInt(1, projectId);
            int rowsAffected = ptmt.executeUpdate();
            logger.info("{} row(s) affected by task deletion for project ID {}", rowsAffected, projectId);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred while deleting task: {}", ex.getMessage());
            return false;
        }
    }

    public List<Tasks> viewTaskDetails(int projectId) {
        try {
            logger.info("Viewing task details for project ID {}", projectId);
            List<Tasks> tasks = taskDAOImp.getTaskDetailByProjectId(projectId);
            if (tasks != null && !tasks.isEmpty()) {
                return tasks;
            } else {
                logger.warn("No tasks found for project ID: {}", projectId);
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("An error occurred while viewing task details: {}", e.getMessage());
            e.printStackTrace();
            return null; // Return null to indicate error
        }
    }
}
