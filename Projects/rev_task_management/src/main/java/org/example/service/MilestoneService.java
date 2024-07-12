package org.example.service;

import org.example.dao.MilestoneDAOImp;
import org.example.model.Milestones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import static org.example.service.Validation.getValidUserId;

public class MilestoneService {
    private static final Logger logger = LoggerFactory.getLogger(MilestoneService.class);
    private static Scanner sc = new Scanner(System.in);
    private static MilestoneDAOImp milestoneDAO = new MilestoneDAOImp();

    public static void addMilestone() {
        logger.info("Starting milestone creation process.");

        Milestones milestone = new Milestones();

        System.out.print("Enter Milestone Name: ");
        milestone.setMilestone_name(sc.nextLine());


        System.out.print("Enter Milestone Description: ");
        milestone.setMilestone_description(sc.nextLine());

        milestone.setCreated_at(new Timestamp(System.currentTimeMillis()));
        milestone.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        Milestones addedMilestone = milestoneDAO.create(milestone);
        if (addedMilestone != null) {
            logger.info("Milestone added successfully. Milestone ID: {}", addedMilestone.getMilestone_id());
            System.out.println("Milestone added successfully.");
        } else {
            logger.error("Failed to add milestone.");
            System.out.println("Failed to add milestone.");
        }
    }

    public static void updateMilestone() {
        logger.info("Starting milestone update process.");

        System.out.print("Enter Milestone ID to update: ");
        int milestoneId = getValidUserId(sc);
        sc.nextLine();

        Milestones milestone = milestoneDAO.getId(milestoneId);
        if (milestone == null) {
            logger.warn("Milestone not found for update. Milestone ID: {}", milestoneId);
            System.out.println("Milestone not found.");
            return;
        }

        System.out.print("Enter new Milestone Name: ");
        milestone.setMilestone_name(sc.nextLine());

        System.out.print("Enter new Milestone Description: ");
        milestone.setMilestone_description(sc.nextLine());

        milestone.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        boolean success = milestoneDAO.update(milestone);
        if (success) {
            logger.info("Milestone updated successfully. Milestone ID: {}", milestoneId);
            System.out.println("Milestone updated successfully.");
        } else {
            logger.error("Failed to update milestone. Milestone ID: {}", milestoneId);
            System.out.println("Failed to update milestone.");
        }
    }

    public static void deleteMilestone() {
        logger.info("Starting milestone deletion process.");

        System.out.print("Enter Milestone ID to delete: ");
        int milestoneId = getValidUserId(sc);
        sc.nextLine();

        boolean success = milestoneDAO.delete(milestoneId);
        if (success) {
            logger.info("Milestone deleted successfully. Milestone ID: {}", milestoneId);
            System.out.println("Milestone deleted successfully.");
        } else {
            logger.error("Failed to delete milestone. Milestone ID: {}", milestoneId);
            System.out.println("Failed to delete milestone.");
        }
    }

    public static void getMilestoneById() {
        logger.info("Starting milestone retrieval process by ID.");

        System.out.print("Enter Milestone ID to retrieve: ");
        int milestoneId = getValidUserId(sc);
        sc.nextLine();

        Milestones milestone = milestoneDAO.getId(milestoneId);
        if (milestone != null) {
            logger.info("Milestone details retrieved successfully. Milestone ID: {}", milestoneId);
            System.out.println("Milestone Details:");
            System.out.println("ID: " + milestone.getMilestone_id());
            System.out.println("Name: " + milestone.getMilestone_name());
            System.out.println("Description: " + milestone.getMilestone_description());
            System.out.println("Created At: " + milestone.getCreated_at());
            System.out.println("Updated At: " + milestone.getUpdated_at());
        } else {
            logger.warn("Milestone not found. Milestone ID: {}", milestoneId);
            System.out.println("Milestone not found.");
        }
    }

    public static void getAllMilestones() {
        logger.info("Retrieving all milestones.");

        List<Milestones> milestonesList = milestoneDAO.getAll();
        if (!milestonesList.isEmpty()) {
            System.out.println("All Milestones:");
            for (Milestones milestone : milestonesList) {
                System.out.println("ID: " + milestone.getMilestone_id() + ", Name: " + milestone.getMilestone_name() + ", Description: " + milestone.getMilestone_description() + ", Created At: " + milestone.getCreated_at() + ", Updated At: " + milestone.getUpdated_at());
            }
        } else {
            logger.warn("No milestones found.");
            System.out.println("No milestones found.");
        }
    }
}
