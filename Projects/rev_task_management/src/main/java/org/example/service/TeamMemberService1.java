package org.example.service;

import org.example.dao.TaskDAOImp;
import org.example.dao.TeamMemberDAOImpl;
import org.example.model.Projects;
import org.example.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TeamMemberService1 {
    private static final Logger logger = LoggerFactory.getLogger(TeamMemberService1.class);
    private TaskDAOImp taskDAOImp;
    private Connection con;
    private TeamMemberDAOImpl teamMemberDAO;

    public TeamMemberService1(TaskDAOImp taskDAOImp, Connection con) {
        this.taskDAOImp = taskDAOImp;
        this.con = con;
    }


    public List<Tasks> viewAssignedTasks(int userId) {
        return teamMemberDAO.viewAssignedTasks(userId);
    }

    public Tasks viewTaskDetails(int userId, int taskId) {
        Tasks task = null;
        try {
            logger.info("Viewing details for task ID {} for user ID: {}", taskId, userId);

            task = teamMemberDAO.viewTaskDetails(taskId, userId);
            if (task != null) {
                logger.info("Task details retrieved successfully.");
            } else {
                logger.warn("Task not found for task ID {} and user ID {}", taskId, userId);
            }
        } catch (Exception e) {
            logger.error("An error occurred while viewing task details: {}", e.getMessage());
            e.printStackTrace();
        }
        return task;
    }



}
