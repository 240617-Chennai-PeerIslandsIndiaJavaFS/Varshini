package dao;

import org.example.DBConnection;
import org.example.dao.UserDAOImpl;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.Users;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDAOImplementaionTest {
    static UserDAOImpl userDAOImplementation;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        userDAOImplementation = new UserDAOImpl();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }


    @Test
    public void testDelete() {
        boolean res = userDAOImplementation.delete(1);
        assertTrue(res);
    }


    @Test
    public void testGetUserById() {
        Users user = userDAOImplementation.getUserById(4);
        assertNotNull(user);
    }
}


