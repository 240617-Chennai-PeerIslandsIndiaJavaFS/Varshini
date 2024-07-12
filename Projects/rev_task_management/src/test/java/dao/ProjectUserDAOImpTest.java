package dao;

import org.example.DBConnection;
import org.example.dao.ProjectManagerDAOImpl;
import org.example.dao.ProjectUserDAOImp;
import org.example.model.ProjectUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectUserDAOImpTest {
    static ProjectUserDAOImp projectUserDAOImp;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        projectUserDAOImp = new ProjectUserDAOImp();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }

    @Test
    public void TestGetId(){
        ProjectUser projectUser = projectUserDAOImp.getId(3);
        assertNotNull(projectUser);
    }


}
