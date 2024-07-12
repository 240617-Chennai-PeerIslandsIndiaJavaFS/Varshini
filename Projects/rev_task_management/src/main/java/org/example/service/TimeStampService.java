package org.example.service;

import org.example.dao.MilestoneDAOImp;
import org.example.dao.TaskDAOImp;
import org.example.dao.TimeStampDAOImp;
import org.example.model.Milestones;
import org.example.model.Tasks;
import org.example.model.TimeStamps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import static org.example.service.Validation.getValidUserId;

public class TimeStampService {
    private static final Logger logger = LoggerFactory.getLogger(TimeStampService.class);
    private static Scanner sc = new Scanner(System.in);
    private static TimeStampDAOImp timeStampDAO = new TimeStampDAOImp();
    private static MilestoneDAOImp milestoneDAO = new MilestoneDAOImp();
    private static TaskDAOImp taskDAO = new TaskDAOImp();

    public static void addTimeStamp() {
        try {
            logger.info("Adding new time stamp.");

            TimeStamps timeStamp = new TimeStamps();

            System.out.print("Enter Milestone ID: ");
            int milestoneId = getValidUserId(sc);
            sc.nextLine();
            Milestones milestone = milestoneDAO.getId(milestoneId);
            if (milestone == null) {
                logger.warn("Invalid Milestone ID {} entered for adding time stamp.", milestoneId);
                System.out.println("Invalid Milestone ID.");
                return;
            }
            timeStamp.setMilestones(milestone);

            System.out.print("Enter Task ID: ");
            int taskId = getValidUserId(sc);
            sc.nextLine();
            Tasks task = taskDAO.getId(taskId);
            if (task == null) {
                logger.warn("Invalid Task ID {} entered for adding time stamp.", taskId);
                System.out.println("Invalid Task ID.");
                return;
            }
//            timeStamp.setTasks(task);
//
//            timeStamp.setTimestamp(new Timestamp(System.currentTimeMillis()));
//
//            TimeStamps addedTimeStamp = timeStampDAO.create(timeStamp);
//            if (addedTimeStamp != null) {
//                logger.info("Time stamp added successfully. Time Stamp ID: {}", addedTimeStamp.getTimeline_id());
//                System.out.println("Time Stamp added successfully.");
//            } else {
//                logger.error("Failed to add time stamp.");
//                System.out.println("Failed to add Time Stamp.");
//            }
        } catch (Exception e) {
            logger.error("An error occurred while adding time stamp: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteTimeStamp() {
        try {
            logger.info("Deleting time stamp.");

            System.out.print("Enter Time Stamp ID to delete: ");
            int timeStampId = getValidUserId(sc);
            sc.nextLine();

            boolean success = timeStampDAO.delete(timeStampId);
            if (success) {
                logger.info("Time stamp deleted successfully. Time Stamp ID: {}", timeStampId);
                System.out.println("Time Stamp deleted successfully.");
            } else {
                logger.warn("Failed to delete time stamp. Time Stamp ID: {}", timeStampId);
                System.out.println("Failed to delete Time Stamp.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while deleting time stamp: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getTimeStampById() {
        try {
            logger.info("Retrieving time stamp by ID.");

            System.out.print("Enter Time Stamp ID to retrieve: ");
            int timeStampId =getValidUserId(sc);
            sc.nextLine();

            TimeStamps timeStamp = timeStampDAO.getId(timeStampId);
            if (timeStamp != null) {
                System.out.println("Time Stamp Details:");
                System.out.println("Time Stamp ID: " + timeStamp.getTimeline_id());
                System.out.println("Milestone ID: " + timeStamp.getMilestones().getMilestone_id());
                System.out.println("Task ID: " + timeStamp.getTasks().getTask_id());
                System.out.println("Time Stamp: " + timeStamp.getTimestamp());
            } else {
                logger.warn("Time stamp not found. Time Stamp ID: {}", timeStampId);
                System.out.println("Time Stamp not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving time stamp: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getAllTimeStamps() {
        try {
            logger.info("Retrieving all time stamps.");

            List<TimeStamps> timeStampsList = timeStampDAO.getAll();
            if (!timeStampsList.isEmpty()) {
                System.out.println("All Time Stamps:");
                for (TimeStamps timeStamp : timeStampsList) {
                    System.out.println("Time Stamp ID: " + timeStamp.getTimeline_id() + ", Milestone ID: " + timeStamp.getMilestones().getMilestone_id() + ", Task ID: " + timeStamp.getTasks().getTask_id() + ", Time Stamp: " + timeStamp.getTimestamp());
                }
            } else {
                logger.warn("No time stamps found.");
                System.out.println("No time stamps found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving all time stamps: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
