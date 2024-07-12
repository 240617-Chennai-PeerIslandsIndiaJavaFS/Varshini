package dao;

import org.example.DBConnection;
import org.example.dao.ProjectDAOImp;
import org.example.dao.ProjectManagerDAOImpl;
import org.example.model.Projects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDAOImpTest {

    static ProjectDAOImp projectDAOImp;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        projectDAOImp = new ProjectDAOImp();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }


    @Test
    public void testUpdate() {
        String query = "UPDATE projects SET client_id = ?, project_name = ?, description = ?, start_date = ?, end_date = ?, percentage_left = ?, manager_id = ?, updated_at = ? WHERE project_id = ?";

        Projects project = new Projects();
        project.setProject_name("Rethu");
        project.setClient_id(1);
        project.setProject_id(3);
        project.setDescription("main project");
        project.setStart_date(new Date(2024 - 12 - 12));
        project.setEnd_date(new Date(2024 - 12 - 12));
        project.setPercentage_left(0);
        project.setManager_id(2);
        project.setUpdated_at(Timestamp.from(Instant.now()));

        boolean res = projectDAOImp.update(project);
        assertTrue(res);
    }

    @Test
    public void testGetId() {
        Projects res = projectDAOImp.getId(3);
        assertNotNull(res);
    }

}