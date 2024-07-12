package org.example.service;

import org.example.DBConnection;
import org.example.dao.*;
import org.example.model.*;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.service.Validation.*;

public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    static Scanner sc = new Scanner(System.in);
    static AdminDAOImp admin;
    static Connection con;
    static UserDAOImpl userDAOImplementation = new UserDAOImpl();
    static TaskDAOImp taskDAOImp = new TaskDAOImp();
    static ProjectDAOImp projectDAOImp = new ProjectDAOImp();

    public AdminService() {
        admin = new AdminDAOImp();
        con = DBConnection.getConnection();
    }

    static AdminDAOImp adminDAOImp;

    public AdminService(AdminDAOImp dao) {
        this.adminDAOImp = dao;

    }

    public AdminService(AdminDAOImp adminDAOImp, Connection connection, UserDAOImpl userDAOImplementation, TaskDAOImp taskDAOImp) {
        this.adminDAOImp = adminDAOImp;

    }



    public void displayAdminMenu() {
        logger.info("Displaying admin menu.");

        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Create User");
            System.out.println("2. Update User");
            System.out.println("3. Deactivate User");
            System.out.println("4. Assign Role");
            System.out.println("5. Create Client");
            System.out.println("6. Create Project");
            System.out.println("7. Track User Details");
            System.out.println("8. Monitor Task Detail");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = getValidUserId(sc);
//            sc.nextLine();

            switch (choice) {
                case 1:
                    logger.info("Selected action: Create User");
                    addUSer();
                    break;
                case 2:
                    logger.info("Selected action: Update User");
                    updateUser();
                    break;
                case 3:
                    logger.info("Selected action: Deactivate User");
                    deactivateUser();
                    break;
                case 4:
                    logger.info("Selected action: Assign Role");
                    assignRole();
                    break;
                case 5:
                    logger.info("Selected action: Create Client");
                    addClient();
                    break;
                case 6:
                    logger.info("Selected action: Create Project");
                    addProject();
                    break;
                case 7:
                    logger.info("Selected action: Track User Details");
                    trackUserDetails();
                    break;
                case 8:
                    logger.info("Selected action: Monitor Task Completion");
                    MonitorTaskDetails();
                    break;
                case 9:
                    logger.info("Logging out.");
                    return;
                default:
                    logger.warn("Invalid choice entered: {}", choice);
                    break;
            }
        }
    }

    //replace void users also static
    public static void addUSer() {
        logger.info("Starting user creation process.");

        String name;
        do {
            System.out.print("Enter name: ");
            name = sc.nextLine();
            if (!isValidName(name)) {
                System.out.println("Invalid name. Please enter a name starting with a capital letter followed by lowercase letters, with a minimum length of 3 and a maximum length of 25 characters.");
            }
        } while (!isValidName(name));

        String roleString;
        do {
            System.out.print("Select user role (ADMIN, PROJECT_MANAGER, TEAM_MEMBER): ");
            roleString = sc.nextLine();
            if (!isValidRole(roleString)) {
                System.out.println("Invalid role. Please enter one of the following: ADMIN, PROJECT_MANAGER, TEAM_MEMBER.");
            }
        } while (!isValidRole(roleString));

        String newEmail;
        do {
            System.out.print("Enter email: ");
            newEmail = sc.nextLine();
            if (!isValidEmail(newEmail)) {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        } while (!isValidEmail(newEmail));

        String newPassword;
        do {
            System.out.print("Enter password: ");
            newPassword = sc.nextLine();
            if (!isValidPassword(newPassword)) {
                System.out.println("Invalid password format. Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
            }
        } while (!isValidPassword(newPassword));

        String phone;
        do {
            System.out.print("Enter phone: ");
            phone = sc.nextLine();
            if (!isValidPhoneNumber(phone)) {
                System.out.println("Invalid phone number format. Please enter a valid international phone number starting with '+'.");
            }
        } while (!isValidPhoneNumber(phone));

        System.out.print("Enter specialization in technology (Frontend, Backend): ");

        String spec = sc.nextLine();

        Users users = new Users();
        users.setUser_name(name);
        users.setUser_role(Role.valueOf(roleString));
        users.setSpecilization(spec);
        users.setPassword(newPassword);
        users.setEmail(newEmail);
        users.setPhone(phone);

        admin.createUser(users);
//        adminDAOImp.createUser(users);
        logger.info("User creation process completed.");

    }

    public static void updateUser() {
        logger.info("Starting user update process.");

        Users user = UserUpdate.updateUser();
        System.out.println(user.getEmail());
        boolean success = admin.updateUser(user);
        System.out.println(success);

        if (success) {
            System.out.println("User updated successfully.");
            logger.info("User updated successfully.");


        } else {
            System.out.println("User update failed.");
            logger.error("User update failed.");

        }
    }

    public static void deactivateUser() {//validation
        logger.info("Starting user deactivation process.");

        List<Users> listOfUsers = userDAOImplementation.getAll();
        for (Users user : listOfUsers) {
            System.out.println(user.getUser_id() + "    " + user.getUser_name().toUpperCase() + "  " + user.getUser_role() + "  " + user.getStatus() + "    " + user.getSpecilization());
            logger.info("User details: ID={}, Name={}, Role={}, Status={}, Specialization={}",
                    user.getUser_id(), user.getUser_name(), user.getUser_role(), user.getStatus(), user.getSpecilization());
        }

        System.out.print("Enter user ID of the user you want to deactivate: ");
        int userId = getValidUserId(sc);
        sc.nextLine();
        Users user = userDAOImplementation.getUserById(userId);
        boolean success = false;
        if (user != null) {
            success = admin.deactivateUser(userId);
        }

        if (success) {
            logger.info("User deactivated successfully.");
        } else {
            logger.error("User deactivation failed.");
        }
    }

    public static void assignRole() {
        logger.info("Starting assign role process.");

        List<Users> listOfUsers = userDAOImplementation.getAll();
        for (Users user : listOfUsers) {
            System.out.println(user.getUser_id() + "    " + user.getUser_name().toUpperCase() + "  " + user.getUser_role() + "  " + user.getStatus() + "    " + user.getSpecilization());
            logger.info("User details: ID={}, Name={}, Role={}, Status={}, Specialization={}",
                    user.getUser_id(), user.getUser_name(), user.getUser_role(), user.getStatus(), user.getSpecilization());
        }

        System.out.print("Enter user ID of the user you want to assign a new role: ");
        int userId = getValidUserId(sc);
        sc.nextLine();
        Users user = userDAOImplementation.getUserById(userId);

        if (user == null) {
            logger.warn("User not found with the provided ID.");
            System.out.println("User not found with the provided ID.");
            return;
        }

        System.out.print("Want to assign a new role to the user (YES) or (NO)?: ");
        String newRoleDecision = sc.nextLine().toUpperCase();

        if (user.getUser_role() == null || newRoleDecision.equals("YES")) {
            System.out.print("Enter new role (PROJECT_MANAGER, TEAM_MEMBER): ");
            String newRoleString = sc.nextLine().toUpperCase();

            try {
                Role role = Role.valueOf(newRoleString);

                boolean success;
                if (user.getUser_role() == role) {
                    logger.warn("The user already has the same role.");
                    System.out.println("The user already has the same role.");
                    return;
                } else if (role == Role.PROJECT_MANAGER) {
                    success = admin.setManagerId(userId, role);
                } else {
                    success = admin.assignRole(userId, role);
                }
                if (success) {
                    logger.info("Role assigned successfully.");
                } else {
                    logger.error("Role assignment failed.");
                }
            } catch (IllegalArgumentException e) {
                logger.error("Invalid role. Please enter a valid role.", e);
                System.out.println("Invalid role. Please enter a valid role.");
            }
        } else {
            logger.info("No changes made to the user's role.");
            System.out.println("No changes made to the user's role.");
        }
    }

    public static void addClient() {
        logger.info("Starting client creation process.");

        String clientName;
        do {
            System.out.print("Enter client name: ");

            clientName = sc.nextLine();
            if (!isValidName(clientName)) {
                System.out.println("Invalid name. Please enter a name starting with a capital letter followed by lowercase letters, with a minimum length of 3 and a maximum length of 25 characters.");
            }
        } while (!isValidName(clientName));


        System.out.print("Enter client company name: ");
        String clientCompanyName = sc.nextLine();

        String clientEmail;
        do {
            System.out.print("Enter client email: ");
            clientEmail = sc.nextLine();
            if (!isValidEmail(clientEmail)) {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        } while (!isValidEmail(clientEmail));


        String clientPhone;
        do {
            System.out.print("Enter client phone: ");
            clientPhone = sc.nextLine();
            if (!isValidPhoneNumber(clientPhone)) {
                System.out.println("Invalid phone number format. Please enter a valid international phone number starting with '+'.");
            }
        } while (!isValidPhoneNumber(clientPhone));

        Date createdAt = new Date();
        Date updatedAt = new Date();
        Clients client = new Clients();
        client.setClient_name(clientName);
        client.setClient_company_name(clientCompanyName);
        client.setEmail(clientEmail);
        client.setPhoneNumber(clientPhone);
        client.setCreatedAt(createdAt);
        client.setUpdatedAt(updatedAt);

        admin.createClient(client);

        logger.info("Client creation process completed.");
    }

    public static void addProject() {
        logger.info("Starting project creation process.");

        ClientDAOImp clientDAOImp = new ClientDAOImp();
        List<Clients> clients = clientDAOImp.getAll();
        System.out.println("Select a client for the project:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((clients.get(i).getClient_id() + ". " + clients.get(i).getClient_name() + " (" + clients.get(i).getClient_company_name() + ")"));
        }
        System.out.print("Enter client id based above table: ");
        int clientNumber = getValidUserId(sc);
        Clients selectedClient = clientDAOImp.getId(clientNumber);
        Projects projects = new Projects();
        System.out.print("Enter project name: ");
        String projectName = sc.nextLine();
        System.out.print("Enter project description: ");
        String projectDescription = sc.nextLine();
        System.out.println("Project Managers List : ");

        UserDAOImpl user = new UserDAOImpl();
        List<Users> u = user.getAll();
        for (int i = 0; i < u.size(); i++) {
            if (u.get(i).getUser_role().equals(Role.PROJECT_MANAGER))
                System.out.println(u.get(i).getUser_id() + " " + u.get(i).getUser_name() + " " + u.get(i).getUser_role());
        }
        System.out.print("add manager id to the project : ");

        int manager_id = getValidUserId(sc);
        projects.setManager_id(manager_id);

        System.out.print("Percentage left :");
        Float percentageLeft = sc.nextFloat();
        projects.setClients(selectedClient);
        projects.setProject_name(projectName);
        projects.setDescription(projectDescription);
        projects.setPercentage_left(percentageLeft);

        admin.createProject(projects);
        System.out.println("Project creation process completed.");
        logger.info("Project creation process completed.");

        String query = "SELECT * from projects where project_name = ?";
        Projects project = new Projects();

        try {
            PreparedStatement pt = con.prepareStatement(query);
            pt.setString(1, projectName);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                project.setProject_id(rs.getInt(1));
                project.setClient_id(rs.getInt(2));
                project.setProject_name(rs.getString(3));
                project.setDescription(rs.getString(4));
                project.setManager_id(rs.getInt(8));
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while fetching project details from database.", e);
            throw new RuntimeException(e);
        }
        createTeam(project);
    }

    public static void createTeam(Projects project) {
        logger.info("Creating team for project: {}", project.getProject_name());

        System.out.print("Enter team name : ");
        String name = sc.next();
        System.out.println(name + " " + project.getProject_id() + " " + project.getManager_id());
        logger.info("Team name entered: {}", name);
        boolean team = AdminDAOImp.createTeamForProject(name, project.getProject_id(), project.getManager_id());
        if (team) {
            logger.info("Team created successfully");
            System.out.println("Team created successfully");
            sc.nextLine();
        } else {
            logger.error("Team creation failed");
            System.out.println("Team not formed");
        }
    }

    public static void trackUserDetails() {
        logger.info("Starting track user details process.");

        List<Users> listOfUsers = userDAOImplementation.getAll();
        for (Users user : listOfUsers) {
            System.out.println(user.getUser_id() + " " + user.getUser_name() + " " + user.getUser_role() + " " + user.getStatus() + " " + user.getSpecilization());
            logger.info("User details: ID={}, Name={}, Role={}, Status={}, Specialization={}",
                    user.getUser_id(), user.getUser_name(), user.getUser_role(), user.getStatus(), user.getSpecilization());
        }

        System.out.print("Enter user ID of the user you want to see user details : ");
        int userId = getValidUserId(sc);
        Users user = userDAOImplementation.getUserById(userId);

        if (user != null) {
            logger.info("User details: ID={}, Name={}, Email={}, Specialization={}, Status={}",
                    user.getUser_id(), user.getUser_name(), user.getEmail(), user.getSpecilization(), user.getStatus());
            System.out.println(user.getUser_id() + "    " + user.getUser_name().toUpperCase() + "   " + user.getEmail() + "    " + user.getSpecilization() + "  " + user.getStatus());
        } else {
            logger.warn("User ID not valid.");
            System.out.println("User ID not valid.");
        }
    }

    public static void MonitorTaskDetails() {
        logger.info("Starting monitor task details process.");
        System.out.println("List of projects : ");
        List<Projects> listOfProjects = projectDAOImp.getAll();
        for (Projects project : listOfProjects) {
            System.out.println("Project ID: " + project.getProject_id() + " Project Name: " + project.getProject_name());
            logger.info("Project details: ID={}, Name={}", project.getProject_id(), project.getProject_name());
        }

        System.out.print("Enter project ID of the project you want to see task details: ");
        int projectId = getValidUserId(sc);
        Projects project = projectDAOImp.getId(projectId);

        if (project != null) {
            List<Tasks> taskDetails = taskDAOImp.getTaskDetailByProjectId(projectId);

            if (taskDetails != null && !taskDetails.isEmpty()) {
                System.out.println("List of tasks for the project:");
                for (Tasks task : taskDetails) {
                    System.out.println("Task ID: " + task.getTask_id() + " Task Name: " + task.getTask_name());
                }

                System.out.print("Enter task ID you want to see: ");
                int taskId = getValidUserId(sc);
                Tasks task = taskDAOImp.getId(taskId);

                if (task != null) {
                    System.out.println("Task ID: " + task.getTask_id() + " Task Name: " + task.getTask_name() + " Milestone ID: " + task.getMilestone_id() + " Updated at: " + task.getUpdatedAt());
                } else {
                    logger.warn("Task ID not found: {}", taskId);
                    System.out.println("Task ID not found.");
                }
            } else {
                logger.warn("No tasks found for project with ID: {}", projectId);
                System.out.println("No tasks found for this project.");
            }
        } else {
            logger.warn("Project ID not valid: {}", projectId);
            System.out.println("Project ID not valid.");
        }
    }
}



