package org.example.service;

import org.example.dao.ClientDAOImp;
import org.example.dao.ProjectDAOImp;
import org.example.dao.UserDAOImpl;
import org.example.model.Clients;
import org.example.model.Projects;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import static org.example.service.Validation.*;

public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    private static Scanner sc = new Scanner(System.in);
    private static ProjectDAOImp projectDAO = new ProjectDAOImp();
    private static ClientDAOImp clientDAO = new ClientDAOImp();
    private static UserDAOImpl userDAO = new UserDAOImpl();

    public static void addProject() {
        Projects project = new Projects();

        try {
            logger.info("Starting to add a new project.");

            System.out.print("Enter Client ID: ");
            int clientId = getValidUserId(sc);
            sc.nextLine();
            Clients client = clientDAO.getId(clientId);
            if (client == null) {
                logger.warn("Invalid Client ID entered: {}", clientId);
                System.out.println("Invalid Client ID.");
                return;
            }
            project.setClients(client);

            String projectName;

            while (true) {
                System.out.print("Enter Project Name: ");
                projectName = sc.nextLine();

                if (isValidProjectName(projectName)) {
                    project.setProject_name(projectName);
                    System.out.println("Valid project name: " + projectName);
                    break;
                } else {
                    String formattedName = formatProjectName(projectName);
                    System.out.println("Invalid project name. Suggested format: " + formattedName);
                    System.out.print("Do you want to accept the suggested format? (yes/no): ");
                    String choice = sc.nextLine();

                    if (choice.equalsIgnoreCase("yes")) {
                        project.setProject_name(formattedName);
                        System.out.println("Accepted project name: " + formattedName);
                        break;
                    } else {
                        System.out.println("Please re-enter the project name.");
                    }
                }
            }

            System.out.print("Enter Description: ");
            project.setDescription(sc.nextLine());

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            project.setStart_date(Date.valueOf(sc.nextLine()));

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            String endDateInput = sc.nextLine();
            if (!endDateInput.isEmpty()) {
                project.setEnd_date(Date.valueOf(endDateInput));
            }

            System.out.print("Enter Percentage Left: ");
            project.setPercentage_left((float) sc.nextDouble());
            sc.nextLine();

            System.out.print("Enter User ID: ");
            int userId =getValidUserId(sc);
            sc.nextLine();
            Users user = userDAO.getUserById(userId);
            if (user == null) {
                logger.warn("Invalid User ID entered: {}", userId);
                System.out.println("Invalid User ID.");
                return;
            }
            project.setUsers(user);

            project.setCreated_at(new Timestamp(System.currentTimeMillis()));
            project.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            Projects addedProject = projectDAO.create(project);
            if (addedProject != null) {
                logger.info("Project added successfully. Project ID: {}", addedProject.getProject_id());
                System.out.println("Project added successfully.");
            } else {
                logger.error("Failed to add project.");
                System.out.println("Failed to add project.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while adding project: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateProject() {
        try {
            logger.info("Starting to update a project.");

            System.out.print("Enter Project ID to update: ");
            int projectId =getValidUserId(sc);
            sc.nextLine();

            Projects project = projectDAO.getId(projectId);
            if (project == null) {
                logger.warn("Project not found for ID: {}", projectId);
                System.out.println("Project not found.");
                return;
            }

            System.out.print("Enter new Client ID: ");
            int clientId = getValidUserId(sc);
            sc.nextLine();
            Clients client = clientDAO.getId(clientId);
            if (client == null) {
                logger.warn("Invalid Client ID entered: {}", clientId);
                System.out.println("Invalid Client ID.");
                return;
            }
            project.setClients(client);

            System.out.print("Enter new Project Name: ");
            project.setProject_name(sc.nextLine());

            System.out.print("Enter new Description: ");
            project.setDescription(sc.nextLine());

            System.out.print("Enter new Start Date (YYYY-MM-DD): ");
            project.setStart_date(Date.valueOf(sc.nextLine()));

            System.out.print("Enter new End Date (YYYY-MM-DD): ");
            String endDateInput = sc.nextLine();
            if (!endDateInput.isEmpty()) {
                project.setEnd_date(Date.valueOf(endDateInput));
            }

            System.out.print("Enter new Percentage Left: ");
            project.setPercentage_left((float) sc.nextDouble());
            sc.nextLine();

            System.out.print("Enter new User ID: ");
            int userId = sc.nextInt();
            sc.nextLine();
            Users user = userDAO.getUserById(userId);
            if (user == null) {
                logger.warn("Invalid User ID entered: {}", userId);
                System.out.println("Invalid User ID.");
                return;
            }
            project.setUsers(user);

            project.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            boolean success = projectDAO.update(project);
            if (success) {
                logger.info("Project updated successfully. Project ID: {}", project.getProject_id());
                System.out.println("Project updated successfully.");
            } else {
                logger.error("Failed to update project.");
                System.out.println("Failed to update project.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating project: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteProject() {
        try {
            logger.info("Starting to delete a project.");

            System.out.print("Enter Project ID to delete: ");
            int projectId = getValidUserId(sc);
            sc.nextLine();

            boolean success = projectDAO.delete(projectId);
            if (success) {
                logger.info("Project deleted successfully. Project ID: {}", projectId);
                System.out.println("Project deleted successfully.");
            } else {
                logger.error("Failed to delete project.");
                System.out.println("Failed to delete project.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting project: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getProjectById() {
        try {
            logger.info("Starting to retrieve a project by ID.");

            System.out.print("Enter Project ID to retrieve: ");
            int projectId = getValidUserId(sc);
            sc.nextLine();

            Projects project = projectDAO.getId(projectId);
            if (project != null) {
                System.out.println("Project Details:");
                System.out.println("ID: " + project.getProject_id());
                System.out.println("Client ID: " + project.getClients().getClient_id());
                System.out.println("Project Name: " + project.getProject_name());
                System.out.println("Description: " + project.getDescription());
                System.out.println("Start Date: " + project.getStart_date());
                System.out.println("End Date: " + project.getEnd_date());
                System.out.println("Percentage Left: " + project.getPercentage_left());
                System.out.println("User ID: " + project.getUsers().getUser_id());
                System.out.println("Created At: " + project.getCreated_at());
                System.out.println("Updated At: " + project.getUpdated_at());
            } else {
                logger.warn("Project not found for ID: {}", projectId);
                System.out.println("Project not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving project: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getAllProjects() {
        try {
            logger.info("Starting to retrieve all projects.");

            List<Projects> projectsList = projectDAO.getAll();
            if (!projectsList.isEmpty()) {
                System.out.println("All Projects:");
                for (Projects project : projectsList) {
                    System.out.println("ID: " + project.getProject_id() + ", Client ID: " + project.getClients().getClient_id() + ", Project Name: " + project.getProject_name() + ", Description: " + project.getDescription() + ", Start Date: " + project.getStart_date() + ", End Date: " + project.getEnd_date() + ", Percentage Left: " + project.getPercentage_left() + ", User ID: " + project.getUsers().getUser_id() + ", Created At: " + project.getCreated_at() + ", Updated At: " + project.getUpdated_at());
                }
            } else {
                logger.warn("No projects found.");
                System.out.println("No projects found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving all projects: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
