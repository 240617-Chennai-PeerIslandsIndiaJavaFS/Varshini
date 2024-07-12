package org.example.service;

import org.example.dao.UserDAOImpl;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

import static org.example.service.Validation.*;
import static org.example.service.Validation.isValidRole;

public class UserUpdate {

    private static final Logger logger = LoggerFactory.getLogger(UserUpdate.class);
    private static Scanner sc = new Scanner(System.in);
    private static UserDAOImpl userDAOImplementation = new UserDAOImpl();

    public static Users updateUser() {
        try {
            List<Users> listOfUsers = userDAOImplementation.getAll();
            for (Users user : listOfUsers) {
                System.out.println(user.getUser_id() + "    " + user.getUser_name().toUpperCase() + "  " + user.getUser_role() + "  " + user.getStatus() + "    " + user.getSpecilization());
            }

            System.out.print("Enter user ID of the user you want to update: ");
            int userId = getValidUserId(sc);
            Users user = userDAOImplementation.getUserById(userId);
            sc.nextLine();

            if (user != null) {
                boolean updating = true;

                while (updating) {
                    System.out.println("Select the field you want to update:");
                    System.out.println("1. Name");
                    System.out.println("2. User Role");
                    System.out.println("3. Email");
                    System.out.println("4. Password");
                    System.out.println("5. Phone");
                    System.out.println("6. Specialization");
                    System.out.println("7. Status");
                    System.out.println("8. Manager ID");
                    System.out.println("9. Finish updating");

                    int choice = sc.nextInt();
                    sc.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.print("Enter new name: ");
                            String name;
                            do {
                                System.out.print("Enter name: ");
                                name = sc.nextLine();
                                if (!isValidName(name)) {
                                    System.out.println("Invalid name. Please enter a name starting with a capital letter followed by lowercase letters, with a minimum length of 3 and a maximum length of 25 characters.");
                                }
                            } while (!isValidName(name));
                            user.setUser_name(name);
                            logger.info("Updated name for user ID {}: {}", userId, name);
                            break;
                        case 2:
                            System.out.print("Select new user role (ADMIN, PROJECT_MANAGER, TEAM_MEMBER): ");
                            String roleString;
                            do {
                                System.out.print("Select new user role (ADMIN, PROJECT_MANAGER, TEAM_MEMBER): ");
                                try {
                                    roleString = String.valueOf(Role.valueOf(sc.nextLine().toUpperCase()));
                                } catch (IllegalArgumentException e) {
                                    roleString = null; // Handle invalid input by setting roleString to null
                                }

                                if (roleString == null || !isValidRole(roleString)) {
                                    System.out.println("Invalid role. Please enter one of the following: ADMIN, PROJECT_MANAGER, TEAM_MEMBER.");
                                }
                            } while (roleString == null || !isValidRole(roleString));

                            user.setUser_role(Role.valueOf(roleString));
                            logger.info("Updated user role for user ID {}: {}", userId, roleString);
                            break;
                        case 3:
                            String newEmail;
                            do {
                                System.out.print("Enter  new email: ");
                                newEmail = sc.nextLine();
                                if (!isValidEmail(newEmail)) {
                                    System.out.println("Invalid email format. Please enter a valid email address.");
                                }
                            } while (!isValidEmail(newEmail));

                            user.setEmail(newEmail);
                            logger.info("Updated email for user ID {}: {}", userId, newEmail);
                            break;
                        case 4:
                            String newPassword;
                            do {
                                System.out.print("Enter password: ");
                                newPassword = sc.nextLine();
                                if (!isValidPassword(newPassword)) {
                                    System.out.println("Invalid password format. Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
                                }
                            } while (!isValidPassword(newPassword));

                            user.setPassword(newPassword);
                            logger.info("Updated password for user ID {}", userId);
                            break;
                        case 5:
                            String phone;
                            do {
                                System.out.print("Enter phone: ");
                                phone = sc.nextLine();
                                if (!isValidPhoneNumber(phone)) {
                                    System.out.println("Invalid phone number format. Please enter a valid international phone number starting with '+'.");
                                }
                            } while (!isValidPhoneNumber(phone));
                            user.setPhone(phone);
                            logger.info("Updated phone number for user ID {}: {}", userId, phone);
                            break;
                        case 6:
                            System.out.print("Enter new specialization (Frontend, Backend): ");
                            String newSpecialization = getValidSpecialization(sc);
                            user.setSpecilization(newSpecialization);
                            logger.info("Updated specialization for user ID {}: {}", userId, newSpecialization);
                            break;
                        case 7:
                            Status newStatus;
                            do {
                                System.out.print("Enter new status (ACTIVE, INACTIVE): ");
                                try {
                                    newStatus = Status.valueOf(sc.nextLine().toUpperCase());
                                } catch (IllegalArgumentException e) {
                                    newStatus = null;
                                }

                                if (newStatus == null || !isValidStatus(newStatus)) {
                                    System.out.println("Invalid status. Please enter one of the following: ACTIVE, INACTIVE.");
                                }
                            } while (newStatus == null || !isValidStatus(newStatus));

                            user.setStatus(newStatus);
                            logger.info("Updated status for user ID {}: {}", userId, newStatus);
                            break;

                        case 8:
                            System.out.print("Enter new manager ID: ");
                            int newManagerId = getValidUserId(sc);
                            sc.nextLine();
                            user.setManager_id(newManagerId);
                            logger.info("Updated manager ID for user ID {}: {}", userId, newManagerId);
                            break;
                        case 9:
                            updating = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }
                }

                return user;
            } else {
                logger.warn("User not found with ID: {}", userId);
                System.out.println("User not found with the provided ID.");
                return null;
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating user: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
