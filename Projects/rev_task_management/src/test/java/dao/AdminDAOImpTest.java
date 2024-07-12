package dao;

import org.example.DBConnection;
import org.example.dao.AdminDAOImp;
import org.example.model.*;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.example.dao.AdminDAOImp.logger;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminDAOImpTest {

    static AdminDAOImp admin;
    static Connection con;

    @BeforeAll
    public void setUpBeforeClass() throws SQLException {
        admin = new AdminDAOImp();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public void tearDownAfterClass() {
        DBConnection.closeConnection();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        // Clean up before each test

    }

    @Test
    public void testCreateUser() {
        Users user = new Users();
        user.setUser_name("Alice");
        user.setUser_role(Role.ADMIN);
        user.setEmail("alice@example.com");
        user.setPassword("password123");
        user.setPhone("1234567890");
        user.setSpecilization("Management");

        Users result = admin.createUser(user);

        assertNotNull(result);
        assertEquals(user.getUser_name(), result.getUser_name());
    }



    @Test
    public void testDeactivateUser() {
        Users user = new Users();
        user.setUser_id(2);
        user.setStatus(Status.ACTIVE);

        boolean result = admin.deactivateUser(user.getUser_id());

        assertTrue(result);
    }



    @Test
    public void testTrackUserActivity() {

        ResultSet rs = admin.trackUserActivity(1);

        assertNotNull(rs);
    }

    @Test
    public void testMonitorTaskCompletion() {

        ResultSet rs = admin.monitorTaskCompletion(1);

        assertNotNull(rs);
    }
}
