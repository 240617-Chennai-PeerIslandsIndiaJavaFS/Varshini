package dao;

import org.example.DBConnection;
import org.example.dao.ProjectDAOImp;
import org.example.dao.ProjectManagerDAOImpl;
import org.example.model.Clients;
import org.example.model.Tasks;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectManagerDAOImpTest {
    static ProjectManagerDAOImpl projectManagerDAO;
    static Connection con;


    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        projectManagerDAO = new ProjectManagerDAOImpl();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }


    @Test
    public void testUpdateClient() {

        Clients clients = new Clients();
        clients.setClient_name("Reshu");
        clients.setEmail("reshu22@gmail.com");
        clients.setPhoneNumber("+918767331567");

        boolean res = projectManagerDAO.updateClient(2,clients);

        assertTrue(res);
    }

    @Test
    public void testDeleteClient() {
        boolean res = projectManagerDAO.deleteClient(2);
        assertTrue(res);
    }

}
