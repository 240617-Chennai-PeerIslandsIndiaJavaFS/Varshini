package dao;

import org.example.DBConnection;
import org.example.dao.ClientDAOImp;
import org.example.dao.MilestoneDAOImp;
import org.example.model.Milestones;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MilestoneDAOImpTest {
    static MilestoneDAOImp milestoneDAOImp;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        milestoneDAOImp = new MilestoneDAOImp();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }

    @Test

    public void testCreate() {
        Milestones milestone = new Milestones();
        milestone.setMilestone_name("Milestone 1");
        milestone.setMilestone_description("First milestone");
        milestone.setCreated_at(Timestamp.from(Instant.now()));
        milestone.setUpdated_at(Timestamp.from(Instant.now()));

        Milestones res = milestoneDAOImp.create(milestone);
        assertNotNull(res);

}

    @Test
    public void testDelete(){

        boolean val = milestoneDAOImp.delete(1);
        assertTrue(val);

    }

    @Test
    public void testGetId() {

        Milestones milestones = milestoneDAOImp.getId(2);
        assertNotNull(milestones);
    }
    @Test
    public void testGetAll() {
        List<Milestones> list = milestoneDAOImp.getAll();
        assertNotNull(list);
        assertEquals(2,list.size());
    }




}
