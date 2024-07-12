package org.example.dao;

import org.example.DBConnection;
import org.example.model.Clients;
import org.example.model.Projects;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImp implements ProjectDAO<Projects> {
    private static final Logger logger = LoggerFactory.getLogger(ProjectDAOImp.class);

    static Connection con;

    public ProjectDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public Projects create(Projects project) {
        String query = "INSERT INTO projects(client_id, project_name, description, start_date, end_date, percentage_left, manager_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ptmt.setInt(1, project.getClient_id());
            ptmt.setString(2, project.getProject_name());
            ptmt.setString(3, project.getDescription());
            ptmt.setDate(4, project.getStart_date());
            ptmt.setDate(5, project.getEnd_date());
            ptmt.setDouble(6, project.getPercentage_left());
            ptmt.setInt(7, project.getManager_id());
            ptmt.setTimestamp(8, project.getCreated_at());
            ptmt.setTimestamp(9, project.getUpdated_at());
            ptmt.executeUpdate();

            ResultSet rs = ptmt.getGeneratedKeys();
            if (rs.next()) {
                project.setProject_id(rs.getInt(1));
            }
            logger.info("Project created successfully: {}", project);
            return project;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM projects WHERE project_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            int row = ptmt.executeUpdate();
            logger.info("{} row(s) deleted", row);
            return row > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return false;
    }
    //project lo clientid dhaggara change chesa  project.getClients().getClient_id()
    @Override
    public boolean update(Projects project) {
        String query = "UPDATE projects SET client_id = ?, project_name = ?, description = ?, start_date = ?, end_date = ?, percentage_left = ?, manager_id = ?, updated_at = ? WHERE project_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, project.getClient_id());
            ptmt.setString(2, project.getProject_name());
            ptmt.setString(3, project.getDescription());
            ptmt.setDate(4, project.getStart_date());
            ptmt.setDate(5, project.getEnd_date());
            ptmt.setDouble(6, project.getPercentage_left());
            ptmt.setInt(7, project.getManager_id());
            ptmt.setTimestamp(8, project.getUpdated_at());
            ptmt.setInt(9, project.getProject_id());
            int row = ptmt.executeUpdate();
            logger.info("{} row(s) updated", row);
            return row > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public Projects getId(int id) {
        String query = "SELECT * FROM projects WHERE project_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Projects project = new Projects();
                project.setProject_id(rs.getInt("project_id"));
                project.setClient_id(rs.getInt("client_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStart_date(rs.getDate("start_date"));
                project.setEnd_date(rs.getDate("end_date"));
                project.setPercentage_left((float) rs.getDouble("percentage_left"));
                project.setManager_id(rs.getInt("manager_id"));
                project.setCreated_at(rs.getTimestamp("created_at"));
                project.setUpdated_at(rs.getTimestamp("updated_at"));
                logger.info("Retrieved project with ID: {}", id);
                return project;
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Projects> getAll() {
        String query = "SELECT * FROM projects";
        List<Projects> projectsList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Projects project = new Projects();
                project.setProject_id(rs.getInt("project_id"));
                Clients clients = new Clients();
                clients.setClient_id(rs.getInt("client_id"));
                project.setClients(clients);
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStart_date(rs.getDate("start_date"));
                project.setEnd_date(rs.getDate("end_date"));
                project.setPercentage_left((float) rs.getDouble("percentage_left"));
                project.setManager_id(rs.getInt("manager_id"));
                project.setCreated_at(rs.getTimestamp("created_at"));
                project.setUpdated_at(rs.getTimestamp("updated_at"));
                projectsList.add(project);
            }
            logger.info("Retrieved all projects");
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return projectsList;
    }
}