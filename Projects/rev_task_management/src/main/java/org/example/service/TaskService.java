package org.example.service;

import org.example.dao.MilestoneDAOImp;
import org.example.dao.ProjectDAOImp;
import org.example.dao.TaskDAOImp;
import org.example.dao.UserDAOImpl;
import org.example.model.Projects;
import org.example.model.Tasks;
import org.example.model.Milestones;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import static org.example.service.Validation.getValidUserId;

public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private static Scanner sc = new Scanner(System.in);
    private static TaskDAOImp taskDAO = new TaskDAOImp();
    private static ProjectDAOImp projectDAO = new ProjectDAOImp();
    private static MilestoneDAOImp milestoneDAO = new MilestoneDAOImp();
    private static UserDAOImpl userDAO = new UserDAOImpl();

    public static void addTask() {
        try {
            logger.info("Starting to add a new task.");

            Tasks task = new Tasks();

            System.out.print("Enter Project ID: ");
            int projectId = getValidUserId(sc);
            sc.nextLine();
            Projects project = projectDAO.getId(projectId);
            if (project == null) {
                logger.warn("Invalid Project ID: {}", projectId);
                System.out.println("Invalid Project ID.");
                return;
            }
            task.setProjects(project);

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            task.setStart_date(Date.valueOf(sc.nextLine()));

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            String endDateInput = sc.nextLine();
            if (!endDateInput.isEmpty()) {
                task.setEnd_date(Date.valueOf(endDateInput));
            }

            System.out.print("Enter Task Name: ");
            task.setTask_name(sc.nextLine());

            System.out.print("Enter Percentage: ");
            task.setPercentage(sc.nextDouble());
            sc.nextLine();

            System.out.print("Enter Milestone ID: ");
            int milestoneId = getValidUserId(sc);
            sc.nextLine();
            Milestones milestone = milestoneDAO.getId(milestoneId);
            if (milestone == null) {
                logger.warn("Invalid Milestone ID: {}", milestoneId);
                System.out.println("Invalid Milestone ID.");
                return;
            }
            task.setMilestone(milestone);

            System.out.print("Enter User ID: ");
            int userId =getValidUserId(sc);
            sc.nextLine();
            Users user = userDAO.getUserById(userId);
            if (user == null) {
                logger.warn("Invalid User ID: {}", userId);
                System.out.println("Invalid User ID.");
                return;
            }
            task.setUser(user);

            task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Tasks addedTask = taskDAO.create(task);
            if (addedTask != null) {
                logger.info("Task added successfully. Task ID: {}", addedTask.getTask_id());
                System.out.println("Task added successfully.");
            } else {
                logger.error("Failed to add task.");
                System.out.println("Failed to add task.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while adding task: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateTask() {
        try {
            logger.info("Starting to update a task.");

            System.out.print("Enter Task ID to update: ");
            int taskId = sc.nextInt();
            sc.nextLine();

            Tasks task = taskDAO.getId(taskId);
            if (task == null) {
                logger.warn("Task not found for update. Task ID: {}", taskId);
                System.out.println("Task not found.");
                return;
            }

            System.out.print("Enter new Project ID: ");
            int projectId = getValidUserId(sc);
            sc.nextLine();
            Projects project = projectDAO.getId(projectId);
            if (project == null) {
                logger.warn("Invalid Project ID: {}", projectId);
                System.out.println("Invalid Project ID.");
                return;
            }
            task.setProjects(project);

            System.out.print("Enter new Start Date (YYYY-MM-DD): ");
            task.setStart_date(Date.valueOf(sc.nextLine()));

            System.out.print("Enter new End Date (YYYY-MM-DD): ");
            String endDateInput = sc.nextLine();
            if (!endDateInput.isEmpty()) {
                task.setEnd_date(Date.valueOf(endDateInput));
            }

            System.out.print("Enter new Task Name: ");
            task.setTask_name(sc.nextLine());

            System.out.print("Enter new Percentage: ");
            task.setPercentage(sc.nextDouble());
            sc.nextLine();

            System.out.print("Enter new Milestone ID: ");
            int milestoneId = getValidUserId(sc);
            sc.nextLine();
            Milestones milestone = milestoneDAO.getId(milestoneId);
            if (milestone == null) {
                logger.warn("Invalid Milestone ID: {}", milestoneId);
                System.out.println("Invalid Milestone ID.");
                return;
            }
            task.setMilestone(milestone);

            System.out.print("Enter new User ID: ");
            int userId = getValidUserId(sc);
            sc.nextLine();
            Users user = userDAO.getUserById(userId);
            if (user == null) {
                logger.warn("Invalid User ID: {}", userId);
                System.out.println("Invalid User ID.");
                return;
            }
            task.setUser(user);

            System.out.print("Enter new Description: ");
            task.setDescription(sc.nextLine());

            task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            boolean success = taskDAO.update(task);
            if (success) {
                logger.info("Task updated successfully. Task ID: {}", task.getTask_id());
                System.out.println("Task updated successfully.");
            } else {
                logger.error("Failed to update task.");
                System.out.println("Failed to update task.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating task: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteTask() {
        try {
            logger.info("Starting to delete a task.");

            System.out.print("Enter Task ID to delete: ");
            int taskId = getValidUserId(sc);
            sc.nextLine();

            boolean success = taskDAO.delete(taskId);
            if (success) {
                logger.info("Task deleted successfully. Task ID: {}", taskId);
                System.out.println("Task deleted successfully.");
            } else {
                logger.error("Failed to delete task.");
                System.out.println("Failed to delete task.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting task: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getTaskById() {
        try {
            logger.info("Starting to retrieve a task by ID.");

            System.out.print("Enter Task ID to retrieve: ");
            int taskId = getValidUserId(sc);
            sc.nextLine();

            Tasks task = taskDAO.getId(taskId);
            if (task != null) {
                System.out.println("Task Details:");
                System.out.println("Task ID: " + task.getTask_id());
                System.out.println("Project ID: " + task.getProject_id());
                System.out.println("Start Date: " + task.getStart_date());
                System.out.println("End Date: " + task.getEnd_date());
                System.out.println("Task Name: " + task.getTask_name());
                System.out.println("Percentage: " + task.getPercentage());
                System.out.println("Milestone ID: " + task.getMilestone().getMilestone_id());
                System.out.println("User ID: " + task.getUser().getUser_id());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Created At: " + task.getCreatedAt());
                System.out.println("Updated At: " + task.getUpdatedAt());
            } else {
                logger.warn("Task not found for ID: {}", taskId);
                System.out.println("Task not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving task: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getAllTasks() {
        try {
            logger.info("Starting to retrieve all tasks.");

            List<Tasks> tasksList = taskDAO.getAll();
            if (!tasksList.isEmpty()) {
                System.out.println("All Tasks:");
                for (Tasks task : tasksList) {
                    System.out.println("Task ID: " + task.getTask_id() + ", Project ID: " + task.getProjects().getProject_id() + ", Start Date: " + task.getStart_date() + ", End Date: " + task.getEnd_date() + ", Task Name: " + task.getTask_name() + ", Percentage: " + task.getPercentage() + ", Milestone ID: " + task.getMilestone().getMilestone_id() + ", User ID: " + task.getUser().getUser_id() + ", Description: " + task.getDescription() + ", Created At: " + task.getCreatedAt() + ", Updated At: " + task.getUpdatedAt());
                }
            } else {
                logger.warn("No tasks found.");
                System.out.println("No tasks found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving all tasks: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
