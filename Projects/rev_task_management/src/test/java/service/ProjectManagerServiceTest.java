package service;

import org.example.dao.MilestoneDAOImp;
import org.example.dao.ProjectDAOImp;
import org.example.dao.TaskDAOImp;
import org.example.dao.TimeStampDAOImp;
import org.example.model.Clients;
import org.example.model.Projects;
import org.example.model.Tasks;
import org.example.service.ProjectManagerService1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ProjectManagerServiceTest {

    @Test
    public void testViewProjectClientDetails() {
        ProjectDAOImp projectDAOImp = Mockito.mock(ProjectDAOImp.class);
        Connection con = Mockito.mock(Connection.class);
        ProjectManagerService1 projectManagerService = new ProjectManagerService1(projectDAOImp, con);

        int projectId = 1;
        Projects project = new Projects();
        project.setProject_id(projectId);
        project.setClient_id(101);

        Clients client = new Clients();
        client.setClient_id(101);
        client.setClient_name("Client Name");
        client.setClient_company_name("Client Company");
        client.setEmail("client@example.com");
        client.setPhoneNumber("1234567890");

        when(projectDAOImp.getId(anyInt())).thenReturn(project);

        try {
            PreparedStatement ptmt = Mockito.mock(PreparedStatement.class);
            ResultSet rs = Mockito.mock(ResultSet.class);

            when(con.prepareStatement(anyString())).thenReturn(ptmt);
            when(ptmt.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getInt(1)).thenReturn(client.getClient_id());
            when(rs.getString(2)).thenReturn(client.getClient_name());
            when(rs.getString(3)).thenReturn(client.getClient_company_name());
            when(rs.getString(4)).thenReturn(client.getEmail());
            when(rs.getString(5)).thenReturn(client.getPhoneNumber());

            Clients result = projectManagerService.viewProjectClientDetails(projectId);

            verify(projectDAOImp).getId(projectId);
            verify(ptmt).setInt(1, project.getClient_id());
            verify(ptmt).executeQuery();
            verify(rs).next();
            verify(rs).getInt(1);
            verify(rs).getString(2);
            verify(rs).getString(3);
            verify(rs).getString(4);
            verify(rs).getString(5);

            // Assert that the client details are as expected
            Assertions.assertNotNull(result);
            Assertions.assertEquals(client.getClient_id(), result.getClient_id());
            Assertions.assertEquals(client.getClient_name(), result.getClient_name());
            Assertions.assertEquals(client.getClient_company_name(), result.getClient_company_name());
            Assertions.assertEquals(client.getEmail(), result.getEmail());
            Assertions.assertEquals(client.getPhoneNumber(), result.getPhoneNumber());

        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteTask() {
        ProjectDAOImp projectDAOImp = Mockito.mock(ProjectDAOImp.class);
        Connection con = Mockito.mock(Connection.class);
        ProjectManagerService1 projectManagerService = new ProjectManagerService1(projectDAOImp, con);

        int projectId = 1;

        try {
            PreparedStatement ptmt = Mockito.mock(PreparedStatement.class);

            when(con.prepareStatement(anyString())).thenReturn(ptmt);
            when(ptmt.executeUpdate()).thenReturn(1); // Simulate successful deletion

            boolean result = projectManagerService.deleteTask(projectId);

            verify(con).prepareStatement("DELETE FROM tasks WHERE project_id = ?");
            verify(ptmt).setInt(1, projectId);
            verify(ptmt).executeUpdate();

            Assertions.assertTrue(result, "Task should be deleted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail("SQLException occurred: " + e.getMessage());
        }
    }


}


