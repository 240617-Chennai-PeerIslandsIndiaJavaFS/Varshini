package dao;

import org.example.DBConnection;
import org.example.dao.TeamsDAOImpl;
import org.example.dao.TimeStampDAOImp;
import org.example.model.TimeStamps;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimeStampsDAOImpTest {
    static TimeStampDAOImp timeStampDAOImp;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        timeStampDAOImp = new TimeStampDAOImp();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }

    @Test
    public void testCreate() {
        boolean res = timeStampDAOImp.create(6);
        assertFalse(res);
    }


    @Test
    public void testDelete(){
        boolean res = timeStampDAOImp.delete(1);
        assertFalse(res);
    }

    @Test
    public void testGetId() {
        TimeStamps timeStamps = timeStampDAOImp.getId(2);
        assertNotNull(timeStamps);
    }



}
