package org.example.service;

import org.example.dao.*;
import org.example.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static org.example.service.Validation.getValidUserId;

public class TeamMemberService {
    private static final Logger logger = LoggerFactory.getLogger(TeamMemberService.class);
    private static Scanner sc = new Scanner(System.in);
    private static TeamMemberDAOImpl teamMemberDAO;
    private static TaskDAOImp taskDAOImp = new TaskDAOImp();
    private static ProjectDAOImp projectDAOImp = new ProjectDAOImp();
    private static ClientDAOImp clientDAOImp = new ClientDAOImp();
    private static MilestoneDAOImp milestoneDAOImp = new MilestoneDAOImp();
    private static TimeStampDAOImp timeStampDAOImp = new TimeStampDAOImp();

    public TeamMemberService() {
        teamMemberDAO = new TeamMemberDAOImpl();
    }

    public void displayTeamMemberMenu(int userId) {
        try {
            logger.info("Displaying team member menu for user ID: {}", userId);

            System.out.println("Assigned Tasks:");
            viewAssignedTasks(userId);

            int taskId;
            do {
                System.out.print("Choose which task ID you want to work on: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid task ID.");
                    sc.next(); // Clear the invalid input
                }
                taskId = sc.nextInt();
                sc.nextLine(); // Consume newline character
            } while (!isValidTaskId(taskId));

            Tasks getTask = taskDAOImp.getId(taskId);

            while (true) {
                System.out.println("Choose an action:");
                System.out.println("1. View Task Details");
                System.out.println("2. View Project Details");
                System.out.println("3. View Client Details");
                System.out.println("4. Update Task Status");
                System.out.println("5. Logout");
                System.out.print("Enter your choice: ");

                int choice;
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid choice.");
                    sc.next();
                }
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        viewTaskDetails(userId, getTask.getTask_id());
                        break;
                    case 2:
                        viewProjectDetails(taskId);
                        break;
                    case 3:
                        viewClientDetails(taskId);
                        break;
                    case 4:
                        updateTaskStatus(taskId);
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while displaying team member menu: {}", e.getMessage());
            e.printStackTrace();
        }
    }
    private boolean isValidTaskId(int taskId) {
        return taskId > 0;
    }

    public void viewAssignedTasks(int userId) {
        try {
            logger.info("Viewing assigned tasks for user ID: {}", userId);

            List<Tasks> tasksList = teamMemberDAO.viewAssignedTasks(userId);
            if (tasksList != null && !tasksList.isEmpty()) {
                for (Tasks task : tasksList) {
                    System.out.println("Task ID: " + task.getTask_id() + ", Project ID: " + task.getProject_id() + ", Task Name: " + task.getTask_name() + ", Description: " + task.getDescription() + ", Milestone: " + task.getMilestone_id() + ", Percentage: " + task.getPercentage());
                }
            } else {
                System.out.println("No assigned tasks found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while viewing assigned tasks: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void viewTaskDetails(int userId, int taskId) {
        try {
            logger.info("Viewing details for task ID {} for user ID: {}", taskId, userId);

            Tasks task = teamMemberDAO.viewTaskDetails(taskId, userId);
            if (task != null) {
                System.out.println("Task ID: " + task.getTask_id() + ", Project ID: " + task.getProject_id() + ", Task Name: " + task.getTask_name() + ", Description: " + task.getDescription() + ", Milestone: " + task.getMilestone_id() + ", Percentage: " + task.getPercentage());
            } else {
                System.out.println("Task not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while viewing task details: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void viewProjectDetails(int taskId) {
        try {
            logger.info("Viewing project details for task ID: {}", taskId);

            Tasks task = taskDAOImp.getId(taskId);
            if (task != null) {
                Projects projectDetails = projectDAOImp.getId(task.getProject_id());
                if (projectDetails != null) {
                    System.out.println("Project ID: " + projectDetails.getProject_id() + ", Project Name: " + projectDetails.getProject_name() + ", Project Manager ID: " + projectDetails.getManager_id() + ", Description: " + projectDetails.getDescription());
                } else {
                    System.out.println("No project details found for this task ID.");
                }
            } else {
                System.out.println("No task found for this task ID.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while viewing project details: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void viewClientDetails(int taskId) {
        try {
            logger.info("Viewing client details for task ID: {}", taskId);

            Tasks task = taskDAOImp.getId(taskId);
            if (task != null) {
                Projects projectDetails = projectDAOImp.getId(task.getProject_id());
                if (projectDetails != null) {
                    Clients clientDetails = clientDAOImp.getId(projectDetails.getClient_id());
                    if (clientDetails != null) {
                        System.out.println("Client ID: " + clientDetails.getClient_id() + ", Company Name: " + clientDetails.getClient_company_name() + ", Contact Name: " + clientDetails.getClient_name() + ", Email: " + clientDetails.getEmail() + ", Phone Number: " + clientDetails.getPhoneNumber());
                    } else {
                        System.out.println("No client details found for this project.");
                    }
                } else {
                    System.out.println("No project details found for this task ID.");
                }
            } else {
                System.out.println("No task found for this task ID.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while viewing client details: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateTaskStatus(int taskId) {
        try {
            logger.info("Updating task status for task ID: {}", taskId);

            Tasks task = new Tasks();
            System.out.print("Enter new task percentage: ");
            task.setPercentage(sc.nextDouble());
            sc.nextLine();

            List<Milestones> milestonesDetails = milestoneDAOImp.getAll();

            for (Milestones milestones : milestonesDetails) {
                System.out.println(milestones.getMilestone_id() + "    " + milestones.getMilestone_name().toUpperCase() + "    " + milestones.getMilestone_description());
            }

            System.out.print("Enter milestone ID based on your project completion: ");
            int milestoneId = getValidUserId(sc);
            Milestones milestones = milestoneDAOImp.getId(milestoneId);
            if (milestones != null)
                task.setMilestone_id(milestoneId);

            boolean success = teamMemberDAO.updateTaskStatus(taskId, task);
            boolean timeStampDetails = timeStampDAOImp.createNewTimeStamp(taskId, milestoneId);

            if (success && timeStampDetails) {
                System.out.println("Task updated successfully.");
            } else {
                System.out.println("Task update failed.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating task status: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
