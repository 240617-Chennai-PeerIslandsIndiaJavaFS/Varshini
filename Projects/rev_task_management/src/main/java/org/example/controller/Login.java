package org.example.controller;

import org.example.dao.StartDAOImp;
import org.example.model.Role;
import org.example.model.Users;
import org.example.service.TeamMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    AdminService adminService = new AdminService();
    ProjectManagerService projectManagerService = new ProjectManagerService();
    TeamMemberService teamMemberService = new TeamMemberService();
    Scanner sc = new Scanner(System.in);

    public void startApplication() throws SQLException {
        logger.info("Starting Task Management application");

        System.out.println("Welcome to Task Management");

        Users user = null;
        StartDAOImp startDAOImp = new StartDAOImp();

        while (user == null) {
            System.out.print("Enter your email: ");
            String email = sc.nextLine();
            logger.debug("User entered email: {}", email);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            logger.debug("User entered password");

            user = startDAOImp.Login(email, password);

            if (user != null) {
                logger.info("Login successful for user: {}", user.getUser_name());
                System.out.println("Login successful");

                if (user.getUser_role().equals(Role.ADMIN)) {
                    logger.info("User {} has ADMIN role", user.getUser_name());
                    adminService.displayAdminMenu();
                } else if (user.getUser_role().equals(Role.PROJECT_MANAGER)) {
                    logger.info("User {} has PROJECT_MANAGER role", user.getUser_name());
                    projectManagerService.displayProjectManagerMenu(user.getUser_id(),user.getEmail(),user.getPassword());
                } else {
                    logger.info("User {} has TEAM_MEMBER role", user.getUser_name());
                    teamMemberService.displayTeamMemberMenu(user.getUser_id());
                }
            } else {
                logger.warn("Login failed for email: {}", email);
                System.out.println("Login failed. Please try again.");

                // Give another chance
                System.out.print("Do you want to try again? (yes/no): ");
                String tryAgain = sc.nextLine().trim().toLowerCase();

                if (!tryAgain.equals("yes")) {
                    break; // Exit the loop if user chooses not to try again
                }
            }
        }
    }
}
