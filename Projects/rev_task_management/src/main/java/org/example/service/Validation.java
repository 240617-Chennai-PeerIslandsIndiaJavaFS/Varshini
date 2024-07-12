package org.example.service;

import org.example.model.Status;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.example.service.AdminService.sc;

public class Validation {
    public static boolean isValidName(String name) {
        String regex = "^[A-Z][a-z]{2,24}$"; // Minimum 3 characters, maximum 25 characters
        return !name.isEmpty() && name.matches(regex);
    }

    public static boolean isValidRole(String roleString) {
        return roleString.equalsIgnoreCase("ADMIN") ||
                roleString.equalsIgnoreCase("PROJECT_MANAGER") ||
                roleString.equalsIgnoreCase("TEAM_MEMBER");
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-z0-9_+&*-]+(?:\\.[a-z0-9_+&*-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        return phone.matches(phoneRegex);
    }

    public static String getValidSpecialization(Scanner sc) {
        String specialization;
        do {
            System.out.print("Enter specialization in technology (Frontend, Backend): ");
            specialization = sc.nextLine().trim(); // Trim to remove any leading or trailing whitespace
            if (!isValidSpecialization(specialization)) {
                System.out.println("Invalid specialization. Please enter either 'Frontend' or 'Backend'.");
            }
        } while (!isValidSpecialization(specialization));
        return specialization;
    }

    public static boolean isValidSpecialization(String specialization) {
        return specialization.equalsIgnoreCase("Frontend") || specialization.equalsIgnoreCase("Backend");
    }

    public static int getValidUserId(Scanner sc) {
        int userId = 0;
        boolean isValidInput = false;
        do {
            try {
                userId = Integer.parseInt(sc.nextLine().trim());
                if (userId > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("User ID must be a positive integer.");
                    isValidInput = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
                isValidInput = false;
            }
        } while (!isValidInput);
        return userId;
    }

    public static String getValidClientName(Scanner sc) {
        String clientName;
        do {
            System.out.print("Enter Client Name (3-25 characters, starting with a capital letter): ");
            clientName = sc.nextLine().trim();
        } while (!isValidClientName(clientName));
        return clientName;
    }

    public static boolean isValidClientName(String name) {
        String regex = "^[A-Z][a-z]{2,24}$"; // Adjusted for 3-25 characters
        return name.matches(regex);
    }

    private static final Set<String> VALID_SDLC_STAGES = new HashSet<>(Arrays.asList(
            "Planning", "Requirements Analysis", "Design", "Implementation", "Testing", "Deployment", "Maintenance"
    ));

    public static boolean isValidSDLCStage(String milestoneName) {
        return VALID_SDLC_STAGES.contains(milestoneName);
    }

    public static Set<String> getValidSDLCStages() {
        return VALID_SDLC_STAGES;
    }

    public boolean isValidDate(CharSequence inputDate) {
        while (true) {
            try {
                LocalDate parsedDate = LocalDate.parse(inputDate);
                System.out.println("Valid date entered: " + parsedDate);
                // Process the valid date further as needed

                return true; // Return true if date format is correct
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                System.out.print("Enter date (YYYY-MM-DD): ");
                inputDate = sc.nextLine().trim();
            }
        }
    }
    public static boolean isValidStatus(Status status) {
        return status == Status.ACTIVE || status == Status.INACTIVE;
    }


    public static boolean isValidProjectName(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            return false;
        }

        // Regex to match the desired format: First letter of each word capitalized, rest lowercase
        String regex = "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$";

        return projectName.matches(regex);
    }

    public static String formatProjectName(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }

        String[] words = projectName.trim().split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return formattedName.toString().trim();
    }
    public static Date getValidDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return java.sql.Date.valueOf((sc.nextLine()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }
}
