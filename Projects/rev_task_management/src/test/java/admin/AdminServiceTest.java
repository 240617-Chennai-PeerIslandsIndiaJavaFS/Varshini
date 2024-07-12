package admin;

import org.example.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

class AdminServiceTest {

    static AdminService adminService;
    static Connection con;

    @BeforeAll
    public static void setUpBeforeClass() throws SQLException {
        adminService = new AdminService();
        con = DBConnection.getConnection();

    }

    @AfterAll
    public static void tearDownAfterClass() {
        DBConnection.closeConnection();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        // Clean up before each test

    }
//
//    @Test
//    void testUpdateUser() {
//        // Arrange
//        Users mockUser = new Users();
//        when(userDAOImplementation.getUserById(anyInt())).thenReturn(mockUser);
//        when(adminDAOImp.updateUser(any(Users.class))).thenReturn(true);
//
//        // Act
//        AdminService.updateUser();
//
//        // Assert
//        verify(adminDAOImp).updateUser(mockUser);
//    }
//
//    @Test
//    void testDeactivateUser() {
//        // Arrange
//        Users mockUser = new Users();
//        when(userDAOImplementation.getAll()).thenReturn(Collections.singletonList(mockUser));
//        when(userDAOImplementation.getUserById(anyInt())).thenReturn(mockUser);
//        when(adminDAOImp.deactivateUser(anyInt())).thenReturn(true);
//
//        // Act
//        AdminService.deactivateUser();
//
//        // Assert
//        verify(adminDAOImp).deactivateUser(anyInt());
//    }
//
//    @Test
//    void testAssignRole() {
//        // Arrange
//        Users mockUser = new Users();
//        when(userDAOImplementation.getAll()).thenReturn(Collections.singletonList(mockUser));
//        when(userDAOImplementation.getUserById(anyInt())).thenReturn(mockUser);
//        when(adminDAOImp.assignRole(anyInt(), any(Role.class))).thenReturn(true);
//
//        // Act
//        AdminService.assignRole();
//
//        // Assert
//        verify(adminDAOImp).assignRole(anyInt(), any(Role.class));
//    }
//
//    @Test
//    void testAddClient() {
//        // Arrange
//        doNothing().when(adminDAOImp).createClient(any(Clients.class));
//
//        // Act
//        AdminService.addClient();
//
//        // Assert
//        ArgumentCaptor<Clients> clientCaptor = ArgumentCaptor.forClass(Clients.class);
//        verify(adminDAOImp).createClient(clientCaptor.capture());
//        Clients capturedClient = clientCaptor.getValue();
//        assertNotNull(capturedClient);
//        assertEquals("ValidClientName", capturedClient.getClient_name());
//        assertEquals("valid@example.com", capturedClient.getEmail());
//    }
//
//    @Test
//    void testAddProject() throws SQLException {
//        // Arrange
//        Clients mockClient = new Clients();
//        when(clientDAOImp.getAll()).thenReturn(Collections.singletonList(mockClient));
//        when(clientDAOImp.getId(anyInt())).thenReturn(mockClient);
//        when(connection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
//
//        // Act
//        AdminService.addProject();
//
//        // Assert
//        verify(adminDAOImp).createProject(any(Projects.class));
//    }
//
//    @Test
//    void testCreateTeam() {
//        // Arrange
//        Projects mockProject = new Projects();
//        doNothing().when(adminDAOImp).createTeamForProject(anyString(), anyInt(), anyInt());
//
//        // Act
//        AdminService.createTeam(mockProject);
//
//        // Assert
//        verify(adminDAOImp).createTeamForProject(anyString(), anyInt(), anyInt());
//    }
//
//    @Test
//    void testTrackUserDetails() {
//        // Arrange
//        Users mockUser = new Users();
//        when(userDAOImplementation.getAll()).thenReturn(Collections.singletonList(mockUser));
//        when(userDAOImplementation.getUserById(anyInt())).thenReturn(mockUser);
//
//        // Act
//        AdminService.trackUserDetails();
//
//        // Assert
//        verify(userDAOImplementation).getUserById(anyInt());
//    }
//
//    @Test
//    void testMonitorTaskDetails() {
//        // Arrange
//        Users mockUser = new Users();
//        when(userDAOImplementation.getAll()).thenReturn(Collections.singletonList(mockUser));
//        when(userDAOImplementation.getUserById(anyInt())).thenReturn(mockUser);
//        when(taskDAOImp.getTaskDetailByUserId(anyInt())).thenReturn(null);
//
//        // Act
//        AdminService.MonitorTaskDetails();
//
//        // Assert
//        verify(taskDAOImp).getTaskDetailByUserId(anyInt());
//    }
}
