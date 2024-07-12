package org.example.dao;

import org.example.DBConnection;
import org.example.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Scanner;

public class AdminDAOImp implements AdminDAO {
    public static final Logger logger = LoggerFactory.getLogger(AdminDAOImp.class);
    public static Scanner sc = new Scanner(System.in);
    static Connection con;

    public AdminDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public Users createUser(Users users) {
        String query = "INSERT into users(user_name, user_role, email, password, phone, specilization) " +
                "values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, users.getUser_name());
            ptmt.setString(2, users.getUser_role().toString());
            ptmt.setString(3, users.getEmail());
            ptmt.setString(4, users.getPassword());
            ptmt.setString(5, users.getPhone());
            ptmt.setString(6, users.getSpecilization());

            int row = ptmt.executeUpdate();
            logger.info("{} row(s) affected", row);
            System.out.println(row + " row(s) affected");
            return users;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateUser(Users users) {
        System.out.println(users.getEmail());
        System.out.println(users.getUser_id());
        String query = "UPDATE users SET user_name = ?, user_role = ?, email = ?, password = ?, phone = ?, specilization = ?, status = ?, manager_id = ?, date_of_joining = ? WHERE user_id = ?";
        System.out.println(users.getPassword());
        System.out.println(users.getEmail());

        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, users.getUser_name());
            ptmt.setString(2, users.getUser_role().toString());
            ptmt.setString(3, users.getEmail());
            ptmt.setString(4, users.getPassword());
            ptmt.setString(5, users.getPhone());
            ptmt.setString(6, users.getSpecilization());
            ptmt.setString(7, users.getStatus().toString());
            ptmt.setInt(8, users.getManager_id());
            ptmt.setDate(9, users.getDate_of_joining());
            ptmt.setInt(10, users.getUser_id());

            int rowsAffected = ptmt.executeUpdate();
            System.out.println(rowsAffected);
            logger.info("Updating user with ID {}: {} row(s) affected", users.getUser_id(), rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred while updating user with ID {}: {}", users.getUser_id(), ex.getMessage());
            throw new RuntimeException("Failed to update user: " + ex.getMessage(), ex);
        }
    }


    @Override
    public boolean deactivateUser(int userId) {
        String query = "UPDATE users SET status = ? WHERE user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, Status.INACTIVE.toString());
            ptmt.setInt(2, userId);

            int rowsAffected = ptmt.executeUpdate();
            logger.info("{} row(s) affected", rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return false;
    }

    public boolean setManagerId(int userId, Role role) {
        String query = "UPDATE users SET user_role = ?, manager_id = ? WHERE user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, role.name());
            ptmt.setNull(2, java.sql.Types.INTEGER);
            ptmt.setInt(3, userId);

            int rowsUpdated = ptmt.executeUpdate();
            logger.info("{} row(s) updated", rowsUpdated);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("SQLException occurred: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean assignRole(int userId, Role newRole) {
        String query = "UPDATE users SET user_role = ? WHERE user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, newRole.toString());
            ptmt.setInt(2, userId);

            int rowsAffected = ptmt.executeUpdate();
            logger.info("{} row(s) affected", rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public Clients createClient(Clients client) {
        String query = "INSERT INTO clients (client_name, client_company_name, client_email, client_phone, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, client.getClient_name());
            ptmt.setString(2, client.getClient_company_name());
            ptmt.setString(3, client.getEmail());
            ptmt.setString(4, client.getPhoneNumber());
            ptmt.setTimestamp(5, new Timestamp(client.getCreatedAt().getTime()));

            int row = ptmt.executeUpdate();
            logger.info("{} row(s) affected", row);
            System.out.println("Client creation process completed.");
            return client;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Projects createProject(Projects project) {
        String query = "INSERT INTO projects (client_id, project_name, description,manager_id,percentage_left) VALUES (?, ?, ?,?,?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, project.getClients().getClient_id());
            ptmt.setString(2, project.getProject_name());
            ptmt.setString(3, project.getDescription());
            ptmt.setInt(4, project.getManager_id());
            ptmt.setFloat(5, project.getPercentage_left());

            int row = ptmt.executeUpdate();
            logger.info("{} row(s) affected", row);
            return project;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return null;
    }

    public static boolean createTeamForProject(String name, int project_id, int manager_id) {
        String query = "INSERT INTO teams (team_name, manager_id, project_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, manager_id);
            pstmt.setInt(3, project_id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Team created successfully");
                return true;
            } else {
                logger.warn("No team was created, no rows affected.");
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred: ", ex);
        }

        return false;
    }

    @Override
    public ResultSet trackUserActivity(int userId) {
        String query = "SELECT * from users where user_id=?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, userId);
            ResultSet rs = ptmt.executeQuery();
            logger.info("Tracking user activity for user ID: {}", userId);
            return rs;
        } catch (SQLException e) {
            logger.error("SQLException occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet monitorTaskCompletion(int task_id) {
        String query = "SELECT * from tasks where task_id=?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, task_id);
            ResultSet rs = ptmt.executeQuery();
            logger.info("Monitoring task completion for task ID: {}", task_id);
            return rs;
        } catch (SQLException e) {
            logger.error("SQLException occurred: {}", e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }
}