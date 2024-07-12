package org.example.dao;

import org.example.DBConnection;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO<Users> {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    static Connection con;

    public UserDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public Users create(Users user) {
        String query = "INSERT INTO users(user_name, user_role, email, password, phone, manager_id, status, specilization, date_of_joining) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ptmt.setString(1, user.getUser_name());
            ptmt.setString(2, user.getUser_role().toString());
            ptmt.setString(3, user.getEmail());
            ptmt.setString(4, user.getPassword());
            ptmt.setString(5, user.getPhone());
            ptmt.setInt(6, user.getManager_id());
            ptmt.setString(7, user.getStatus().toString());
            ptmt.setString(8, user.getSpecilization());
            ptmt.setDate(9, user.getDate_of_joining());

            ptmt.executeUpdate();

            ResultSet rs = ptmt.getGeneratedKeys();
            if (rs.next()) {
                user.setUser_id(rs.getInt(1));
                logger.info("Created user with ID {}", user.getUser_id());
            }
            return user;
        } catch (SQLException ex) {
            logger.error("SQLException occurred while creating user: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, userId);
            int row = ptmt.executeUpdate();
            if (row > 0) {
                logger.info("Deleted user with ID {}", userId);
                return true;
            } else {
                logger.warn("No user found with ID {} to delete", userId);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while deleting user with ID {}: {}", userId, ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Users user) {
        String query = "UPDATE users SET user_name = ?, user_role = ?, email = ?, password = ?, phone = ?, manager_id = ?, status = ?, specilization = ?, date_of_joining = ? WHERE user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, user.getUser_name());
            ptmt.setString(2, user.getUser_role().toString());
            ptmt.setString(3, user.getEmail());
            ptmt.setString(4, user.getPassword());
            ptmt.setString(5, user.getPhone());
            ptmt.setInt(6, user.getManager_id());
            ptmt.setString(7, user.getStatus().toString());
            ptmt.setString(8, user.getSpecilization());
            ptmt.setDate(9, user.getDate_of_joining());
            ptmt.setInt(10, user.getUser_id());

            int row = ptmt.executeUpdate();
            if (row > 0) {
                logger.info("Updated user with ID {}", user.getUser_id());
                return true;
            } else {
                logger.warn("No user found with ID {} to update", user.getUser_id());
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while updating user with ID {}: {}", user.getUser_id(), ex.getMessage());
        }
        return false;
    }

    public boolean updateManagerId(int userId, int managerId) {
        String query = "UPDATE users SET manager_id = ? WHERE user_id = ?";
        try (PreparedStatement ptm = con.prepareStatement(query)) {
            ptm.setInt(1, managerId);
            ptm.setInt(2, userId);

            int row = ptm.executeUpdate();
            if (row > 0) {
                logger.info("Updated manager ID for user with ID {}", userId);
                return true;
            } else {
                logger.warn("No user found with ID {} to update manager ID", userId);
            }
        } catch (SQLException e) {
            logger.error("SQLException occurred while updating manager ID for user with ID {}: {}", userId, e.getMessage());
        }
        return false;
    }
    public boolean updateManagerId(int user_id) {
        String query = "UPDATE users SET manager_id = ? WHERE user_id = ?";
        try (PreparedStatement pt = con.prepareStatement(query)) {
            pt.setNull(1, java.sql.Types.INTEGER);
            pt.setInt(2, user_id);
            int rowsAffected = pt.executeUpdate();
            if (rowsAffected > 0) {
                logger.debug("Manager ID set to null for user with ID {}", user_id);
                return true;
            } else {
                logger.warn("No user found with ID {}", user_id);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error occurred while updating manager ID for user with ID {}: {}", user_id, e.getMessage());
            return false;
        }
    }


    @Override
    public Users getUserById(int id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setUser_name(rs.getString("user_name"));
                String userRoleString = rs.getString("user_role");
                Role userRole = Role.valueOf(userRoleString.toUpperCase());
                user.setUser_role(userRole);
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setManager_id(rs.getInt("manager_id"));
                String statusString = rs.getString("status");
                Status userStatus = Status.valueOf(statusString.toUpperCase());
                user.setStatus(userStatus);
                user.setSpecilization(rs.getString("specilization"));
                user.setDate_of_joining(rs.getDate("date_of_joining"));
                logger.debug("Fetched user details for ID {}", id);
                return user;
            } else {
                System.out.println("No user found with this ID");
                logger.warn("No user found with ID {}", id);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("SQLException occurred while fetching user with ID {}: {}", id, ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Users> getAll() {
        String query = "SELECT * FROM users";
        List<Users> usersList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setUser_name(rs.getString("user_name"));
                String roleString = rs.getString("user_role");
                Role userRole = Role.valueOf(roleString.toUpperCase());
                user.setUser_role(userRole);
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setManager_id(rs.getInt("manager_id"));
                String statusString = rs.getString("status");
                try {
                    Status userStatus = Status.valueOf(statusString.toUpperCase());
                    user.setStatus(userStatus);
                } catch (IllegalArgumentException e) {
                    user.setStatus(Status.PENDING); // Set a default value
                }
                user.setSpecilization(rs.getString("specilization"));
                user.setDate_of_joining(rs.getDate("date_of_joining"));
                usersList.add(user);
            }
            if (!usersList.isEmpty()) {
                logger.info("Fetched {} users from the database", usersList.size());
            } else {
                logger.warn("No users found in the database");
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching users: {}", ex.getMessage());
        }
        return usersList;
    }
}
