package org.example.service;

import org.example.dao.ProjectUserDAOImp;
import org.example.model.ProjectUser;
import org.example.model.Projects;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

import static org.example.service.Validation.getValidUserId;

public class ProjectUserService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectUserService.class);
    private static Scanner sc = new Scanner(System.in);
    private static ProjectUserDAOImp projectUserDAO = new ProjectUserDAOImp();

    public static void addProjectUser() {
        try {
            logger.info("Starting to add a new project user.");

            ProjectUser projectUser = new ProjectUser();

            System.out.print("Enter User ID: ");
            int userId = getValidUserId(sc);
            sc.nextLine();
            Users user = new Users();
            user.setUser_id(userId);
            projectUser.setUsers(user);

            System.out.print("Enter Project ID: ");
            int projectId =getValidUserId(sc);
            sc.nextLine();
            Projects project = new Projects();
            project.setProject_id(projectId);
            projectUser.setProjects(project);

            ProjectUser addedProjectUser = projectUserDAO.create(projectUser);
            if (addedProjectUser != null) {
                logger.info("Project User added successfully. Project User ID: {}", addedProjectUser.getProject_user_id());
                System.out.println("Project User added successfully.");
            } else {
                logger.error("Failed to add Project User.");
                System.out.println("Failed to add Project User.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while adding project user: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteProjectUser() {
        try {
            logger.info("Starting to delete a project user.");

            System.out.print("Enter Project User ID to delete: ");
            int projectUserId = getValidUserId(sc);
            sc.nextLine();

            boolean success = projectUserDAO.delete(projectUserId);
            if (success) {
                logger.info("Project User deleted successfully. Project User ID: {}", projectUserId);
                System.out.println("Project User deleted successfully.");
            } else {
                logger.error("Failed to delete Project User.");
                System.out.println("Failed to delete Project User.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting project user: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getProjectUserById() {
        try {
            logger.info("Starting to retrieve a project user by ID.");

            System.out.print("Enter Project User ID to retrieve: ");
            int projectUserId = getValidUserId(sc);
            sc.nextLine();

            ProjectUser projectUser = projectUserDAO.getId(projectUserId);
            if (projectUser != null) {
                System.out.println("Project User Details:");
                System.out.println("Project User ID: " + projectUser.getProject_user_id());
                System.out.println("User ID: " + projectUser.getUsers().getUser_id());
                System.out.println("Project ID: " + projectUser.getProjects().getProject_id());
            } else {
                logger.warn("Project User not found for ID: {}", projectUserId);
                System.out.println("Project User not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving project user: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getAllProjectUsers() {
        try {
            logger.info("Starting to retrieve all project users.");

            List<ProjectUser> projectUsersList = projectUserDAO.getAll();
            if (!projectUsersList.isEmpty()) {
                System.out.println("All Project Users:");
                for (ProjectUser projectUser : projectUsersList) {
                    System.out.println("Project User ID: " + projectUser.getProject_user_id() + ", User ID: " + projectUser.getUsers().getUser_id() + ", Project ID: " + projectUser.getProjects().getProject_id());
                }
            } else {
                logger.warn("No project users found.");
                System.out.println("No project users found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving all project users: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
