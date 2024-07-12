package service;

import org.example.dao.TaskDAOImp;
import org.example.dao.TeamMemberDAOImpl;
import org.example.model.Tasks;
import org.example.service.TeamMemberService1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.example.dao.AdminDAOImp.logger;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TeamMemberServiceTest {
    private TeamMemberDAOImpl teamMemberDAO;

    @Mock
    private TaskDAOImp taskDAOImp;

    @Mock
    private Connection mockConnection;

    private TeamMemberService1 teamMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        teamMemberService = new TeamMemberService1(taskDAOImp, mockConnection);
    }

    @Test
    void testViewTaskDetails() {
        int userId = 4;
        int taskId = 1;
        Tasks expectedTask = new Tasks();
        expectedTask.setTask_id(taskId);
        expectedTask.setProject_id(4);
        expectedTask.setTask_name("Sample Task");
        expectedTask.setDescription("Task description");
        expectedTask.setMilestone_id(2);
        expectedTask.setPercentage(50.0);

        when(teamMemberDAO.viewTaskDetails(taskId, userId)).thenReturn(expectedTask);

        Tasks actualTask = teamMemberService.viewTaskDetails(userId, taskId);

        assertEquals(expectedTask, actualTask, "Returned task should match expected task");

        verify(logger).info("Viewing details for task ID {} for user ID: {}", taskId, userId);
        verify(logger).info("Task details retrieved successfully.");
    }

    @Test
    public void testViewAssignedTasks_TasksFound() {
        List<Tasks> mockTasksList = new ArrayList<>();
        Tasks task1 = new Tasks();
        task1.setTask_id(1);
        task1.setProject_id(100);
        task1.setTask_name("Task 1");
        task1.setDescription("Description of Task 1");
        task1.setMilestone_id(1);
        task1.setPercentage(50.0);
        mockTasksList.add(task1);

        Tasks task2 = new Tasks();
        task2.setTask_id(2);
        task2.setProject_id(101);
        task2.setTask_name("Task 2");
        task2.setDescription("Description of Task 2");
        task2.setMilestone_id(2);
        task2.setPercentage(75.0);
        mockTasksList.add(task2);

        when(teamMemberDAO.viewAssignedTasks(anyInt())).thenReturn(mockTasksList);

        List<Tasks> result = teamMemberService.viewAssignedTasks(1);

        assertNotNull(result);
        assertEquals(mockTasksList.size(), result.size());
        assertEquals(mockTasksList.get(0), result.get(0));
        assertEquals(mockTasksList.get(1), result.get(1));
        verify(teamMemberDAO, times(1)).viewAssignedTasks(1);
    }

}
