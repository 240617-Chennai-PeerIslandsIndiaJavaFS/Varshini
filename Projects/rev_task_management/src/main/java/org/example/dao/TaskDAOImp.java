package org.example.dao;

import org.example.DBConnection;
import org.example.model.Milestones;
import org.example.model.Projects;
import org.example.model.Tasks;
import org.example.model.Users;
import org.example.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImp implements TaskDAO<Tasks> {

    private static final Logger logger = LoggerFactory.getLogger(TaskDAOImp.class);

    static Connection con;

    public TaskDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public Tasks create(Tasks task) {
        logger.info("Starting task creation process.");

        String query = "INSERT INTO tasks(project_id, start_date, end_date, task_name, percentage, milestone_id, user_id, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, task.getProject_id());
            ptmt.setDate(2, task.getStart_date());
            ptmt.setDate(3, task.getEnd_date());
            ptmt.setString(4, task.getTask_name());
            ptmt.setDouble(5, task.getPercentage());
            ptmt.setInt(6, task.getMilestone_id());
            ptmt.setInt(7, task.getUser_id());
            ptmt.setString(8, task.getDescription());
            ptmt.setTimestamp(9, task.getCreatedAt());
            ptmt.setTimestamp(10, task.getUpdatedAt());
            int row = ptmt.executeUpdate();
            System.out.println(row);

            return row>0 ?task:null;
        } catch (SQLException ex) {
            logger.error("Error occurred while creating task: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM tasks WHERE task_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            int row = ptmt.executeUpdate();
            if (row > 0) {
                logger.info("Deleted task with ID {}", id);
                return true;
            } else {
                logger.warn("No task found with ID {} to delete", id);
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while deleting task with ID {}: {}", id, ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Tasks task) {
        String query = "UPDATE tasks SET project_id = ?, start_date = ?, end_date = ?, task_name = ?, percentage = ?, milestone_id = ?, user_id = ?, description = ?, updated_at = ? WHERE task_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, task.getProject_id());
            ptmt.setDate(2, task.getStart_date());
            ptmt.setDate(3, task.getEnd_date());
            ptmt.setString(4, task.getTask_name());
            ptmt.setDouble(5, task.getPercentage());
            ptmt.setInt(6, task.getMilestone_id());
            ptmt.setInt(7, task.getUser_id());
            ptmt.setString(8, task.getDescription());
            ptmt.setTimestamp(9, task.getUpdatedAt());
            ptmt.setInt(10, task.getTask_id());
            int row = ptmt.executeUpdate();
            if (row > 0) {
                logger.info("Updated task with ID {}", task.getTask_id());
                return true;
            } else {
                logger.warn("No task found with ID {} to update", task.getTask_id());
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while updating task with ID {}: {}", task.getTask_id(), ex.getMessage());
        }
        return false;
    }

    @Override
    public Tasks getId(int id) {
        String query = "SELECT * FROM tasks WHERE task_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setProject_id(rs.getInt("project_id"));
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                task.setMilestone_id(rs.getInt("milestone_id"));
                task.setUser_id(rs.getInt("user_id"));
                task.setDescription(rs.getString("description"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                logger.debug("Fetched task with ID {}", id);
                return task;
            } else {
                logger.warn("No task found with ID {}", id);
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while fetching task with ID {}: {}", id, ex.getMessage());
        }
        return null;
    }

    public Tasks getTaskDetailByUserId(int user_id) {
        String query = "SELECT * FROM tasks WHERE user_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, user_id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setProject_id(rs.getInt("project_id"));
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                task.setMilestone_id(rs.getInt("milestone_id"));
                task.setDescription(rs.getString("description"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                logger.debug("Fetched tasks for user with ID {}", user_id);
                return task;
            } else {
                logger.warn("No tasks found for user with ID {}", user_id);
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while fetching tasks for user with ID {}: {}", user_id, ex.getMessage());
        }
        return null;
    }
    public List<Tasks> getTaskDetailByUserId1(int user_id) {
        String query = "SELECT * FROM tasks WHERE user_id = ?";
        List<Tasks> tasksList = new ArrayList<>();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, user_id);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setProject_id(rs.getInt("project_id"));
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                task.setMilestone_id(rs.getInt("milestone_id"));
                task.setDescription(rs.getString("description"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                tasksList.add(task);
            }
            if (tasksList.isEmpty()) {
                logger.warn("No tasks found for user with ID {}", user_id);
            } else {
                logger.debug("Fetched {} tasks for user with ID {}", tasksList.size(), user_id);
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while fetching tasks for user with ID {}: {}", user_id, ex.getMessage());
        }
        return tasksList;
    }


    public List<Tasks> getTaskDetailByProjectId(int project_id) {
        String query = "SELECT * FROM tasks WHERE project_id = ?";
        List<Tasks> list = new ArrayList<>();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, project_id);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setProject_id(rs.getInt("project_id"));
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                task.setMilestone_id(rs.getInt("milestone_id"));
                task.setDescription(rs.getString("description"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                list.add(task);
            }
            if (list.isEmpty()) {
                logger.warn("No tasks found for project with ID {}", project_id);
            } else {
                logger.debug("Fetched {} tasks for project with ID {}", list.size(), project_id);
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while fetching tasks for project with ID {}: {}", project_id, ex.getMessage());
        }
        return list;
    }


    @Override
    public List<Tasks> getAll() {
        String query = "SELECT * FROM tasks";
        List<Tasks> tasksList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                Projects projects = new Projects();
                projects.setProject_id(rs.getInt("project_id"));
                task.setProjects(projects);
                task.setStart_date(rs.getDate("start_date"));
                task.setEnd_date(rs.getDate("end_date"));
                task.setTask_name(rs.getString("task_name"));
                task.setPercentage(rs.getDouble("percentage"));
                Milestones milestones = new Milestones();
                milestones.setMilestone_id(rs.getInt("milestone_id"));
                task.setMilestone(milestones);
                Users users = new Users();
                users.setUser_id(rs.getInt("user_id"));
                task.setUser(users);
                task.setDescription(rs.getString("description"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                tasksList.add(task);
            }
            if (!tasksList.isEmpty()) {
                logger.info("Fetched {} tasks", tasksList.size());
            } else {
                logger.warn("No tasks found");
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while fetching all tasks: {}", ex.getMessage());
        }
        return tasksList;
    }
}
