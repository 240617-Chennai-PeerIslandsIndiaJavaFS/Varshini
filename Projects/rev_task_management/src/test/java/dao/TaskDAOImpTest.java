package dao;

import org.example.DBConnection;
import org.example.dao.ProjectUserDAOImp;
import org.example.dao.TaskDAOImp;
import org.example.model.Tasks;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDAOImpTest {

    static TaskDAOImp taskDAOImp;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        taskDAOImp = new TaskDAOImp();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }



    @Test
    public void testDelete() {
        boolean res = taskDAOImp.delete(2);
        assertTrue(res);
    }



    @Test
    public void testGetId() {
        Tasks res = taskDAOImp.getId(2);
        assertNotNull(res);
    }

}
