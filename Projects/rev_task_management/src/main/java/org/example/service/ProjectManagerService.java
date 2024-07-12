package org.example.service;

import org.example.dao.*;
import org.example.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.example.service.AdminService.sc;
import static org.example.service.Validation.*;

public class ProjectManagerService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectManagerService.class);
    private static Scanner sc = new Scanner(System.in);
    private static ProjectManagerDAOImpl projectManager = new ProjectManagerDAOImpl();
    private UserDAOImpl userDAOImplementation = new UserDAOImpl();
    private static ProjectDAOImp projectDAOImp = new ProjectDAOImp();
    private static MilestoneDAOImp milestoneDAOImp = new MilestoneDAOImp();
    private static TeamsDAOImpl teamsDAO = new TeamsDAOImpl();
    private static ProjectUserDAOImp projectUserDAOImp = new ProjectUserDAOImp();
    private static TimeStampDAOImp timeStampDAOImp = new TimeStampDAOImp();
    static Clients clientsDetails = new Clients();
    static TaskDAOImp taskDAOImp = new TaskDAOImp();

    private static int original_id = 0;

    public ProjectManagerService() {
        projectManager = new ProjectManagerDAOImpl();
    }

    public ProjectManagerService(ProjectDAOImp projectDAOImp, Connection con) {
    }

    public void displayProjectManagerMenu(int userId, String email, String password) throws SQLException {
        try {
            System.out.print("Do you want to reset your password? (yes/no): ");
            String resetChoice = sc.nextLine().trim().toLowerCase();

            if (resetChoice.equals("yes")) {
                resetPassword(email, password);
            }

            List<Projects> projectList = projectDAOImp.getAll();
            logger.info("Displaying projects for manager with ID {}", userId);
            System.out.println("List of projects : ");
            for (Projects project : projectList) {
                if (project.getManager_id() == userId) {
                    System.out.println("Project id : " + project.getProject_id() + " Project Name : " + project.getProject_name() + " Project Manager id :" + project.getManager_id());
                }
            }

            System.out.print("Choose which project id you want to work on: ");
            int project_id1 = sc.nextInt();
            sc.nextLine(); // Clear the newline character from the buffer
            Projects getProject = projectDAOImp.getId(project_id1);

            if (getProject != null) {
                System.out.println("Project id : " + getProject.getProject_id() + " Project Name : " + getProject.getProject_name() + " Project Updated at : " + getProject.getUpdated_at());
                original_id = getProject.getProject_id();
                logger.info("Selected project ID {} for operations", original_id);

                boolean running = true;
                while (running) {
                    System.out.println("1. View Project Client Details");
                    System.out.println("2. Add Team Member to Project");
                    System.out.println("3. Remove Team Member from Project");
                    System.out.println("4. Assign Task to Team Member");
                    System.out.println("5. Update Task"); //not include
                    System.out.println("6. Delete Task");
                    System.out.println("7. View Task Details");
                    System.out.println("8. Logout");
                    System.out.print("Enter what you want to do on Project: ");

                    int choice = sc.nextInt();
                    sc.nextLine(); // Clear the newline character from the buffer

                    switch (choice) {
                        case 1:
                            viewProjectClientDetails(original_id);
                            break;
                        case 2:
                            addTeamMemberToProject(original_id, userId);
                            break;
                        case 3:
                            removeTeamMemberFromProject(original_id);
                            break;
                        case 4:
                            assignTaskToTeamMember(original_id);
                            break;
                        case 5:
                            updateTask(original_id);
                            break;
                        case 6:
                            deleteTask(original_id);
                            break;
                        case 7:
                            viewTaskDetails(original_id);
                            break;
                        case 8:
                            System.out.println("Logging out...");
                            running = false; // Exit the loop and return to caller
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else {
                System.out.println("Project not found.");
            }
        } catch (SQLException ex) {
            logger.error("SQL Exception occurred: {}", ex.getMessage());
            throw ex;
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void resetPassword(String email, String password) {
        boolean resetSuccessful = false;

        while (!resetSuccessful) {
            String user_email;
            boolean validEmail;

            // Loop to ensure valid email input
            do {
                System.out.print("Enter email: ");
                user_email = sc.nextLine();
                validEmail = isValidEmail(user_email);
                if (!validEmail) {
                    System.out.println("Invalid email format. Please try again.");
                }
            } while (!validEmail);

            String user_oldPassword;
            boolean validOldPassword;

            // Loop to ensure valid old password input
            do {
                System.out.print("Enter password: ");
                user_oldPassword = sc.nextLine();
                validOldPassword = isValidPassword(user_oldPassword);
                if (!validOldPassword) {
                    System.out.println("Invalid password. Please try again.");
                }
            } while (!validOldPassword);

            if (email.equals(user_email) && password.equals(user_oldPassword)) {
                String newPassword;
                boolean validNewPassword;

                // Loop to ensure valid new password input
                do {
                    System.out.print("Enter new password to reset the password: ");
                    newPassword = sc.nextLine();
                    validNewPassword = isValidPassword(newPassword);
                    if (!validNewPassword) {
                        System.out.println("Invalid new password. Please try again.");
                    }
                } while (!validNewPassword);

                boolean res = projectManager.resetPassword(email, newPassword);
                if (res) {
                    System.out.println("Password reset successfully.");
                    resetSuccessful = true;
                } else {
                    System.out.println("Failed to reset the password. Please try again.");
                }
            } else {
                System.out.println("Email or password does not match our records. Please try again.");
            }
        }
    }


    public static void viewProjectClientDetails(int project_id) {
        try {
            Projects projects = projectDAOImp.getId(project_id);
            if (projects != null) {
                logger.info("Retrieving client details for project ID {}", project_id);
                clientsDetails = projectManager.viewClientDetails(projects.getClient_id());

                if (clientsDetails != null) {
                    System.out.println(clientsDetails.getClient_id() + " " + clientsDetails.getClient_company_name() + " " + clientsDetails.getClient_name() + " " + clientsDetails.getEmail() + " " + clientsDetails.getPhoneNumber());
                }
            } else {
                logger.warn("Project with ID {} not found", project_id);
                System.out.println("Project not found.");
            }
        } catch (Exception e) {
            logger.error("Error retrieving client details: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void addTeamMemberToProject(int project_id, int manager_id) throws SQLException {
        try {
            Projects isTheir = projectDAOImp.getId(project_id);
            logger.info("Adding team member to project with ID {}", project_id);
            System.out.println(isTheir.getProject_name());
            Teams team = teamsDAO.getId(project_id);
            System.out.println(team.getTeam_id());
            TeamMemberDAOImpl teamMemberDAO = new TeamMemberDAOImpl();
            List<Users> list = userDAOImplementation.getAll();

            System.out.println("List of Team Members:");
            int count = 0;
            for (Users user : list) {
                if (user.getManager_id() == 0 && user.getUser_role().equals(Role.TEAM_MEMBER)) {
                    count++;
                    System.out.println("user id  :" + user.getUser_id() + " user name : " + user.getUser_name() + " " + user.getEmail());
                }
            }
            if (count == 0) {
                System.out.println("There are no team members in the users table. An admin needs to create users as team members for this project.");
            }
            System.out.print("Enter user ID based on team member ID in User table : ");
            int userId =  getValidUserId(sc);
            Users user = userDAOImplementation.getUserById(userId);

            if (user != null) {
                System.out.println(team.getTeam_id()+" "+userId);
                if (teamMemberDAO.create(team.getTeam_id(), userId)) {
                    System.out.println("Team member added to the team member table ");
                }
                if (userDAOImplementation.updateManagerId(userId, manager_id)) {
                    System.out.println("Updated project manager for the user ");
                } else {
                    System.out.println("Failed to add team member to the team member table ");
                }
            }

            if (isTheir != null && user.getUser_role().equals(Role.TEAM_MEMBER)) {
                boolean success = projectManager.addTeamMemberToProject(project_id, userId);

                if (success) {
                    System.out.println("Team member added to project successfully.");
                } else {
                    System.out.println("Failed to add team member to project.");
                }
            } else {
                if (isTheir == null) {
                    System.out.println("Project with ID " + project_id + " does not exist.");
                }
                if (user == null) {
                    System.out.println("User with ID " + userId + " does not exist.");
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while adding team member: {}", e.getMessage());
            e.printStackTrace();
        }
    }

//    public void removeTeamMemberFromProject(int projectId) {
//        try {
//            logger.info("Removing team member from project with ID {}", projectId);
//            boolean success = false;
//            System.out.print("Enter user ID: ");
//            int userId =  getValidUserId(sc);
//            Users user = userDAOImplementation.getUserById(userId);
//
//            if (user != null && user.getUser_role().equals(Role.TEAM_MEMBER)) {
//                success = projectManager.removeTeamMemberFromProject(projectId, userId);
//            }
//
//            if (success) {
//                System.out.println("Team member removed from project successfully.");
//            } else {
//                System.out.println("Failed to remove team member from project.");
//            }
//        } catch (Exception e) {
//            logger.error("An error occurred while removing team member: {}", e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public void removeTeamMemberFromProject(int projectId){
        System.out.println("List of users id  based on the project id");
        List<Integer> listOfUser = projectUserDAOImp.getProjectId(projectId);
        if(listOfUser!=null){
            for(Integer id:listOfUser) {
                System.out.println("User id's : "+id);
            }
        }
        System.out.print("enter which team member want to remove from project : ");
        int user_id = getValidUserId(sc);
        Users user = userDAOImplementation.getUserById(user_id);
        System.out.println(user.getUser_id()+" "+user.getUser_name());
        if(user!=null){
            boolean removeUser = projectUserDAOImp.deleteUser(user_id);
            if(removeUser){
                System.out.println("removed successfully");
                boolean updateManagerIdToNull =  userDAOImplementation.updateManagerId(user_id);
                if(updateManagerIdToNull){
                    System.out.println("updated manager id to null to the user");
                }else{
                    System.out.println("manager id not updated to null");
                }

            }else{
                System.out.println("failed to remove user from project");
            }
        }


    }

    public void assignTaskToTeamMember(int projectId) {
        try {
            Tasks task = new Tasks();
//            validation
            task.setProject_id(projectId);
            task.setStart_date(getValidDate("Enter start date (YYYY-MM-DD): "));
            task.setEnd_date(getValidDate("Enter end date (YYYY-MM-DD): "));
            System.out.print("Enter task name: ");
            task.setTask_name(sc.nextLine());

            List<Integer> listOfUsersIds = projectUserDAOImp.getProjectId(projectId);
            logger.info("Assigning task to team member for project ID {}", projectId);
            System.out.println(listOfUsersIds.size());
            if (listOfUsersIds != null) {
                System.out.println("List of users:");
                for (Integer userId : listOfUsersIds) {
                    Users user = userDAOImplementation.getUserById(userId);
                    if (user != null && user.getUser_role() == Role.TEAM_MEMBER) {
                        System.out.println(user.getUser_id() + "  " + user.getUser_name() + "  " +
                                user.getUser_role() + "  " + user.getSpecilization() + "  " + user.getStatus());
                    }
                }
            } else {
                System.out.println("No users found for project ID: " + projectId);
            }

            System.out.print("Enter user ID to assign the task to: ");
            int userId = getValidUserId(sc);
            task.setUser_id(userId);

            System.out.print("Enter task description: ");
            task.setDescription(sc.nextLine());
            task.setMilestone_id(1);

            Tasks assignedTask = projectManager.assignTaskToTeamMember(task, userId);

            if (assignedTask != null) {
                System.out.println("Task assigned to team member successfully.");
                List<Tasks> taskDe = projectManager.getTaskBasedProjectId(assignedTask.getProject_id());


                if (taskDe != null) {
                    for (Tasks taskDetails : taskDe) {
                        System.out.println("taskId: " + taskDetails.getTask_id() +
                                ", projectId: " + taskDetails.getProject_id() +
                                ", Task Name: " + taskDetails.getTask_name() +
                                ", UserId: " + taskDetails.getUser_id());
                    }
                }
                int latestTaskId = -1;
                for (Tasks taskDetails : taskDe) {
                    if (taskDetails.getTask_id() > latestTaskId) {
                        latestTaskId = taskDetails.getTask_id();
                    }
                }

                System.out.println("Choose which task ID you want to store in the timestamps table (or the most recently created task ID will be used by default):");
                int taskId = latestTaskId;
                System.out.println("Most recently created task ID: " + latestTaskId);
                Tasks task1 = taskDAOImp.getId(taskId);
                timeStampDAOImp.create(taskId,task1.getMilestone_id());
            } else {
                System.out.println("Failed to assign task to team member.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while assigning task: {}", e.getMessage());
            e.printStackTrace();
        }
    }


    public void updateTask(int projectId) {
        try {
            System.out.println("List of tasks based on project");
            List<Tasks> taskDetails = taskDAOImp.getTaskDetailByProjectId(projectId);
            if (taskDetails != null) {
                for (Tasks task : taskDetails) {
                    System.out.println("Task id: " + task.getTask_id() + ", Task name: " + task.getTask_name() + ", End date: " + task.getEnd_date() + ", User id: " + task.getUser_id());
                }
            }
            System.out.print("Select which task ID you want to update: ");
            int taskId = getValidUserId(sc);
            Tasks task = taskDAOImp.getId(taskId);
            if (task != null) {
                System.out.print("Enter what you want to update (1 for end date, 2 for user): ");
                System.out.println("1. Update End Date");
                System.out.println("2. Update User");
                int choice = getValidUserId(sc);
                if (choice == 1) {
                    Date date = getValidDate( "Enter new end date (YYYY-MM-DD): ");
                    boolean updateEndDate = projectManager.updateTask1(taskId, (java.sql.Date) date);
                    if (updateEndDate) {
                        System.out.println("End date updated successfully.");
                        Tasks taskDetails1 = taskDAOImp.getId(taskId);
                        if(taskDetails1!=null){
                            boolean timeStampCreated = timeStampDAOImp.create(taskId,taskDetails1.getMilestone_id());
                            if(timeStampCreated){
                                System.out.println("Time stamp also created");
                            }else{
                                System.out.println("Time stamp creation failed");
                            }
                        }
                    } else {
                        System.out.println("End date update failed.");
                    }
                } else if (choice == 2) {
                    System.out.println("List of users working on project:");
                    List<Integer> listOfUsersBasedOnProject = projectUserDAOImp.getProjectId(projectId);
                    if (listOfUsersBasedOnProject != null) {
                        for (Integer user : listOfUsersBasedOnProject) {
                            System.out.println("User ID: " + user);
                        }
                    }
                    System.out.print("Enter the user ID you want to update based on the list: ");
                    int userId = getValidUserId(sc);
                    boolean res = projectManager.updateTaskBasedUserId(taskId, userId);
                    if (res) {
                        System.out.println("User updated successfully.");
                        Tasks taskDetails1 = taskDAOImp.getId(taskId);
                        if(taskDetails1!=null){
                            boolean timeStampCreated = timeStampDAOImp.create(taskId,taskDetails1.getMilestone_id());
                            if(timeStampCreated){
                                System.out.println("Time stamp also created");
                            }else{
                                System.out.println("Time stamp creation failed");
                            }
                        }

                    } else {
                        System.out.println("User update failed.");
                    }
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Task not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating the task: {}", e.getMessage());
            e.printStackTrace();
        }
    }

//    public void updateTask(int projectId) {
//        try {
//            Tasks task = new Tasks();
//            task.setProject_id(projectId);
//            System.out.print("Enter new task percentage: ");
//            task.setPercentage(sc.nextDouble());
//            sc.nextLine();
//
//            List<Milestones> milestonesDetails = milestoneDAOImp.getAll();
//
//            for (Milestones milestones : milestonesDetails) {
//                System.out.println(milestones.getMilestone_id() + "    " + milestones.getMilestone_name().toUpperCase() + "    " + milestones.getMilestone_description());
//            }
//
//            System.out.print("Enter milestone ID based on your project completion : ");
//            int milestoneId = getValidUserId(sc);
//            Milestones milestones = milestoneDAOImp.getId(milestoneId);
//            if (milestones != null)
//                task.setMilestone_id(milestoneId);
//
//            logger.info("Updating task for project ID {}", projectId);
//            boolean success = projectManager.updateTask(projectId, task);
//
//            if (success) {
//                System.out.println("Task updated successfully.");
//            } else {
//                System.out.println("Task update failed.");
//            }
//        } catch (Exception e) {
//            logger.error("An error occurred while updating task: {}", e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public void deleteTask(int projectId) {
        try {
            logger.info("Deleting task for project ID {}", projectId);
            boolean success = projectManager.deleteTask(projectId);

            if (success) {
                System.out.println("Task deleted successfully.");
            } else {
                System.out.println("Task deletion failed.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting task: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void viewTaskDetails(int projectId) {
        try {
            logger.info("Viewing task details for project ID {}", projectId);
            List<Tasks> tasks = projectManager.viewTaskDetails(projectId); //getting tasks based on project id
            if (tasks != null && !tasks.isEmpty()) {
                System.out.println("Task details for project ID :  " + projectId);
                for (Tasks task : tasks) {
                    System.out.println("Task id  :  "+task.getTask_id()+" Task Name :  "+task.getTask_name()+"  Project id  :  "+task.getProject_id()+" Milestone stage : "+task.getMilestone_id());

                }
                System.out.print("Enter task id which to see the task details : ");
                int task_id = getValidUserId(sc);
                List<TimeStamps> timeStamps = timeStampDAOImp.getAllTimeStampBasedTaskId(task_id);

                for (TimeStamps timeStamp : timeStamps) {
                    if (timeStamp != null) {
                        Milestones milestones = milestoneDAOImp.getId(timeStamp.getMilestone_id());
                        System.out.println("TimeLine id : " +timeStamp.getTimeline_id()+" Milestone Name  : "+milestones.getMilestone_name().toUpperCase()+" Time sTamp  :  "+timeStamp.getTimestamp());
                        System.out.println();
                    }
                }

            } else {
                System.out.println("No tasks found for project ID: " + projectId);
            }
        } catch (Exception e) {
            logger.error("An error occurred while viewing task details: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
