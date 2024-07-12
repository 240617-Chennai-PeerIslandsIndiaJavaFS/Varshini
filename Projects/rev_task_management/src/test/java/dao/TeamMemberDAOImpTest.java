package dao;

import org.example.DBConnection;
import org.example.dao.TaskDAOImp;
import org.example.dao.TeamMemberDAOImpl;
import org.example.model.Tasks;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamMemberDAOImpTest {


    static TeamMemberDAOImpl teamMemberDAO;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        teamMemberDAO = new TeamMemberDAOImpl();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }

    @Test
    public void testCreate() {
        boolean res = teamMemberDAO.create(2,5);
        assertTrue(res);

    }


    @Test
    public void testViewAssignedTasks(){
        List<Tasks> list = teamMemberDAO.viewAssignedTasks(1);
        assertEquals(0,list.size());

    }

}
