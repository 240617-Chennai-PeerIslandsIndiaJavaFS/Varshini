package org.example.dao;

import org.example.DBConnection;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StartDAOImp implements LoginDAO {
    private static final Logger logger = LoggerFactory.getLogger(StartDAOImp.class);

    static Connection con;

    public StartDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public Users Login(String email, String password) {
        String query = "SELECT * FROM users WHERE email=? AND password=?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, email);
            ptmt.setString(2, password);
            ResultSet res = ptmt.executeQuery();

            if (res.next()) {
                Users users = new Users();
                users.setUser_id(res.getInt(1));
                users.setUser_name(res.getString(2));
                String userRoleString = res.getString("user_role");
                try {
                    Role userRole = Role.valueOf(userRoleString.toUpperCase());
                    users.setUser_role(userRole);
                } catch (IllegalArgumentException e) {
                    logger.warn("Invalid role from database: {}", userRoleString);
                    users.setUser_role(Role.TEAM_MEMBER);
                }

                users.setEmail(res.getString(4));
                users.setPassword(res.getString(5));
                users.setPhone(res.getString(6));
                users.setManager_id(res.getInt(7));
                String statusString = res.getString(8);
                try {
                    Status status = Status.valueOf(statusString.toUpperCase());
                    users.setStatus(status);
                } catch (IllegalArgumentException e) {
                    logger.warn("Invalid status from database: {}", statusString);
                    users.setStatus(Status.ACTIVE);
                }
                users.setSpecilization(res.getString(9));
                users.setDate_of_joining(res.getDate(10));

                return users;
            } else {
                logger.info("No user found with email: {} and provided password", email);
            }

        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println("SQLException occurred: " + ex.getMessage());
        }
        return null;
    }
}
