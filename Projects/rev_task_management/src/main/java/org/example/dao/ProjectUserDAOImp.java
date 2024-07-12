package org.example.dao;

import org.example.DBConnection;
import org.example.model.ProjectUser;
import org.example.model.Projects;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectUserDAOImp implements ProjectUserDAO<ProjectUser> {
    private static final Logger logger = LoggerFactory.getLogger(ProjectUserDAOImp.class);

    static Connection con;

    public ProjectUserDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public ProjectUser create(ProjectUser projectUser) {
        String query = "INSERT INTO project_users(user_id, project_id) VALUES (?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ptmt.setInt(1, projectUser.getUser_id());
            ptmt.setInt(2, projectUser.getProject_id());

            ptmt.executeUpdate();

            ResultSet rs = ptmt.getGeneratedKeys();
            if (rs.next()) {
                projectUser.setProject_user_id(rs.getInt(1));
            }
            logger.info("Created project user relationship successfully: {}", projectUser);
            return projectUser;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        System.out.println();
        String query = "DELETE FROM project_users WHERE project_user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            int row = ptmt.executeUpdate();
            logger.info("{} project user(s) deleted", row);
            return row > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public ProjectUser getId(int id) {
        String query = "SELECT * FROM project_users WHERE project_user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                ProjectUser projectUser = new ProjectUser();
                projectUser.setProject_user_id(rs.getInt("project_user_id"));
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                projectUser.setUsers(user);
                Projects projects = new Projects();
                projects.setProject_id(rs.getInt("project_id"));
                projectUser.setProjects(projects);

                logger.info("Retrieved project user relationship with ID: {}", id);
                return projectUser;
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return null;
    }


    public List<Integer> getProjectId(int ProjectId) {
        String query = "SELECT user_id FROM project_users WHERE project_id = ?";
        List<Integer> listOfUsers = new ArrayList<>();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, ProjectId);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                listOfUsers.add(rs.getInt("user_id"));
            }
            logger.info("Retrieved user IDs for project ID: {}", ProjectId);
            return listOfUsers;
        } catch (SQLException ex) {
            logger.error("Error fetching user IDs: {}", ex.getMessage());
            System.out.println("Error fetching user IDs: " + ex.getMessage());
        }
        return null;
    }
    public boolean deleteUser(int user_id) {
        String query = "DELETE FROM project_users WHERE user_id = ?";
        try (PreparedStatement ptmt = con.prepareStatement(query)) {
            ptmt.setInt(1, user_id);
            int rowsAffected = ptmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.debug("User with ID {} deleted successfully.", user_id);
                return true;
            } else {
                logger.warn("No user found with ID {}.", user_id);
                return false;
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while deleting user with ID {}: {}", user_id, ex.getMessage());
            return false;
        }
    }



    @Override
    public List<ProjectUser> getAll() {
        String query = "SELECT * FROM project_users";
        List<ProjectUser> projectUsersList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                ProjectUser projectUser = new ProjectUser();
                projectUser.setProject_user_id(rs.getInt("project_user_id"));
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                projectUser.setUsers(user);
                Projects projects = new Projects();
                projects.setProject_id(rs.getInt("project_id"));
                projectUser.setProjects(projects);

                projectUsersList.add(projectUser);
            }
            logger.info("Retrieved all project user relationships");
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return projectUsersList;
    }
}
