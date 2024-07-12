package service;

import org.example.dao.AdminDAOImp;
import org.example.dao.UserDAOImpl;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.Users;
import org.example.service.AdminService1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.example.dao.AdminDAOImp.sc;
import static org.mockito.Mockito.*;

public class AdminServiceTest {
    private static AdminDAOImp adminDAOImp;
    private  static AdminService1 adminService1;
    public AdminServiceTest() {

    }
    @BeforeAll
    public static void setup(){
        adminDAOImp = mock(AdminDAOImp.class);
        adminService1 = new AdminService1(sc, adminDAOImp);
    }
    @Test
    public void testCreateUser() {
        AdminDAOImp adminDAOImp = Mockito.mock(AdminDAOImp.class);
        Scanner sc = Mockito.mock(Scanner.class);
        adminService1 = new AdminService1(sc, adminDAOImp);

        Users user = new Users();
        user.setUser_name("Haritha");
        user.setSpecilization("Frontend");
        user.setUser_role(Role.TEAM_MEMBER);
        user.setEmail("haritha23@gmail.com");
        user.setPhone("+918978054336");
        user.setPassword("Haritha@23");

        when(adminDAOImp.createUser(any(Users.class))).thenReturn(user);

        Users createdUser = adminService1.createUser(user);

        Assertions.assertEquals("Haritha", createdUser.getUser_name());
        Assertions.assertEquals("Frontend", createdUser.getSpecilization());
        Assertions.assertEquals(Role.TEAM_MEMBER, createdUser.getUser_role());
        Assertions.assertEquals("haritha23@gmail.com", createdUser.getEmail());
        Assertions.assertEquals("+918978054336", createdUser.getPhone());
        Assertions.assertEquals("Haritha@23", createdUser.getPassword());

        verify(adminDAOImp).createUser(any(Users.class));
    }

    @Test
    public void testUpdateUser() {
        AdminDAOImp adminDAOImp = Mockito.mock(AdminDAOImp.class);
        Scanner sc = Mockito.mock(Scanner.class);
        AdminService1 adminService = new AdminService1(sc, adminDAOImp);

        Users user = new Users();
        user.setUser_name("Haritha Updated");
        user.setSpecilization("Frontend");
        user.setUser_role(Role.TEAM_MEMBER);
        user.setEmail("haritha23_updated@gmail.com");
        user.setPhone("+918978054336");
        user.setPassword("HarithaUpdated@23");
        user.setUser_id(1);

        when(adminDAOImp.updateUser(any(Users.class))).thenReturn(true);

        boolean success = adminService.updateUser(user);

        Assertions.assertTrue(success);
        verify(adminDAOImp).updateUser(any(Users.class));
    }

}




