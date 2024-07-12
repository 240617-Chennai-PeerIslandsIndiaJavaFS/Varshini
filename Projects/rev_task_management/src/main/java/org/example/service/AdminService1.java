package org.example.service;

import java.util.Scanner;
import java.util.function.Predicate;

import org.example.dao.AdminDAOImp;
import org.example.model.Role;
import org.example.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.service.AdminService.admin;
import static org.example.service.AdminService.userDAOImplementation;

public class AdminService1 {
    private static final Logger logger = LoggerFactory.getLogger(AdminService1.class);
    private Scanner sc;
    private AdminDAOImp adminDAOImp;

    public AdminService1(Scanner sc, AdminDAOImp adminDAOImp) {
        this.sc = sc;
        this.adminDAOImp = adminDAOImp;
    }

    public Users addUser() {
        logger.info("Starting user creation process.");

        String name = promptForValidInput("Enter name: ", this::isValidName, "Invalid name. Please enter a name starting with a capital letter followed by lowercase letters, with a minimum length of 3 and a maximum length of 25 characters.");
        String roleString = promptForValidInput("Select user role (ADMIN, PROJECT_MANAGER, TEAM_MEMBER): ", this::isValidRole, "Invalid role. Please enter one of the following: ADMIN, PROJECT_MANAGER, TEAM_MEMBER.");
        String newEmail = promptForValidInput("Enter email: ", this::isValidEmail, "Invalid email format. Please enter a valid email address.");
        String newPassword = promptForValidInput("Enter password: ", this::isValidPassword, "Invalid password format. Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        String phone = promptForValidInput("Enter phone: ", this::isValidPhoneNumber, "Invalid phone number format. Please enter a valid international phone number starting with '+'.");

        System.out.print("Enter specialization in technology (Frontend, Backend): ");
        String spec = sc.nextLine();

        Users user = new Users();
        user.setUser_name(name);
        user.setUser_role(Role.valueOf(roleString));
        user.setSpecilization(spec);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        user.setPhone(phone);

        Users createdUser = createUser(user);
        logger.info("User creation process completed.");
        return createdUser;
    }

    public Users createUser(Users user) {
        return adminDAOImp.createUser(user);
    }
    public boolean updateUser(Users user) {
        return adminDAOImp.updateUser(user);
    }
    public boolean deactivateUser(int userId) {
        logger.info("Starting user deactivation process.");
        Users user = userDAOImplementation.getUserById(userId);
        if (user != null) {
            return admin.deactivateUser(userId);
        } else {
            logger.error("User with ID {} not found.", userId);
            return false;
        }
    }


    private String promptForValidInput(String prompt, Predicate<String> isValid, String errorMessage) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine();
            if (!isValid.test(input)) {
                System.out.println(errorMessage);
            }
        } while (!isValid.test(input));
        return input;
    }

    // Validation methods like isValidName, isValidRole, isValidEmail, etc.
    private boolean isValidName(String name) {
        return name.matches("[A-Z][a-z]{2,24}");
    }

    private boolean isValidRole(String role) {
        try {
            Role.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^\\+\\d{1,15}$");
    }
}
