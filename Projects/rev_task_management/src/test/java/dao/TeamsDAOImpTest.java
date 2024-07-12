package dao;

import org.example.DBConnection;
import org.example.dao.TeamMemberDAOImpl;
import org.example.dao.TeamsDAOImpl;
import org.example.model.Teams;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamsDAOImpTest {
    static TeamsDAOImpl teamsDAO;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        teamsDAO = new TeamsDAOImpl();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }

    @Test
    public void testCreate() {
        String query = "INSERT INTO teams(team_name, manager_id, project_id) VALUES (?, ?, ?)";
        Teams teams = new Teams();
        teams.setTeam_name("Team Beta");
        teams.setManager_id(2);
        teams.setProject_id(2);
        Teams team = teamsDAO.create(teams);
        assertNotNull(team);

    }

}
