package org.example.dao;

import org.example.DBConnection;
import org.example.model.Milestones;
import org.example.model.Tasks;
import org.example.model.TimeStamps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TimeStampDAOImp implements TimeStampDAO<TimeStamps> {

    private static final Logger logger = LoggerFactory.getLogger(TimeStampDAOImp.class);
    static Connection con;

    public TimeStampDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public boolean create(int task_id) {
        String query = "INSERT INTO time_stamps(task_id) VALUES (?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, task_id);
            ptmt.executeUpdate();

            logger.info("Created time stamp entry for task with ID {}", task_id);
            return true;

        } catch (SQLException ex) {
            logger.error("SQLException occurred while creating time stamp for task with ID {}: {}", task_id, ex.getMessage());
        }
        return false;
    }
    public boolean create(int task_id,int milestone_id) {
        String query = "INSERT INTO time_stamps(task_id,milestone_id) VALUES (?,?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, task_id);
            ptmt.setInt(2,milestone_id);
            ptmt.executeUpdate();

            logger.info("Created time stamp entry for task with ID {}", task_id);
            return true;

        } catch (SQLException ex) {
            logger.error("SQLException occurred while creating time stamp for task with ID {}: {}", task_id, ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM time_stamps WHERE time_stamp_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            int row = ptmt.executeUpdate();

            if (row > 0) {
                logger.info("Deleted time stamp with ID {}", id);
                return true;
            } else {
                logger.warn("No time stamp found with ID {} to delete", id);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while deleting time stamp with ID {}: {}", id, ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(int taskId, int milestoneId) {
        String query = "UPDATE time_stamps SET milestone_id = ? WHERE task_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, milestoneId);
            ptmt.setInt(2, taskId);
            int row = ptmt.executeUpdate();

            if (row > 0) {
                logger.info("Updated milestone ID for time stamp with task ID {}", taskId);
                return true;
            } else {
                logger.warn("No time stamp found with task ID {} to update", taskId);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while updating milestone ID for time stamp with task ID {}: {}", taskId, ex.getMessage());
        }
        return false;
    }
    public boolean createNewTimeStamp(int taskId, int milestoneId) {
        String query = "INSERT INTO time_stamps (task_id, milestone_id, time_stamp) VALUES (?, ?, ?)";

        try {
            PreparedStatement pt = con.prepareStatement(query);
            pt.setInt(1,taskId);
            pt.setInt(2,milestoneId);
            pt.setTimestamp(3,Timestamp.from(Instant.now()));

            int result = pt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TimeStamps getId(int id) {
        String query = "SELECT * FROM time_stamps WHERE time_stamp_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                TimeStamps timeStamp = new TimeStamps();
                timeStamp.setTimeline_id(rs.getInt("time_stamp_id"));
                Milestones milestones = new Milestones();
                milestones.setMilestone_id(rs.getInt("milestone_id"));
                timeStamp.setMilestones(milestones);
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                timeStamp.setTasks(task);
                timeStamp.setTimestamp(rs.getTimestamp("time_stamp"));

                logger.debug("Fetched time stamp details for ID {}", id);
                return timeStamp;
            } else {
                logger.warn("No time stamp found with ID {}", id);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching time stamp with ID {}: {}", id, ex.getMessage());
        }
        return null;
    }

    @Override
    public List<TimeStamps> getAll() {
        String query = "SELECT * FROM time_stamps";
        List<TimeStamps> timeStampsList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                TimeStamps timeStamp = new TimeStamps();
                timeStamp.setTimeline_id(rs.getInt("time_stamp_id"));
                Milestones milestones = new Milestones();
                milestones.setMilestone_id(rs.getInt("milestone_id"));
                timeStamp.setMilestones(milestones);
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                timeStamp.setTasks(task);
                timeStamp.setTimestamp(rs.getTimestamp("time_stamp"));
                timeStampsList.add(timeStamp);
            }
            if (!timeStampsList.isEmpty()) {
                logger.info("Fetched {} time stamps from the database", timeStampsList.size());
            } else {
                logger.warn("No time stamps found in the database");
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching time stamps: {}", ex.getMessage());
        }
        return timeStampsList;
    }

    public List<TimeStamps> getAllTimeStampBasedTaskId(int taskId) {
        String query = "SELECT * FROM time_stamps WHERE task_id = " + taskId;

        List<TimeStamps> timeStampsList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                TimeStamps timeStamp = new TimeStamps();
                timeStamp.setTimeline_id(rs.getInt("time_stamp_id"));
                timeStamp.setMilestone_id(rs.getInt("milestone_id"));
                timeStamp.setTask_id(rs.getInt("task_id"));
                timeStamp.setTimestamp(rs.getTimestamp("time_stamp"));
                timeStampsList.add(timeStamp);
            }
            if (!timeStampsList.isEmpty()) {
                logger.info("Fetched {} time stamps from the database", timeStampsList.size());
            } else {
                logger.warn("No time stamps found in the database");
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching time stamps: {}", ex.getMessage());
        }
        return timeStampsList;
    }


//    public Tasks getTaskIdDetails(int taskId) {
//        String query = "SELECT * from tasks where task_id = ?";
//        try {
//            PreparedStatement pt = con.prepareStatement(query);
//            pt.setInt(1, taskId);
//
//            ResultSet rs = pt.executeQuery();
//
//            if (rs.next()) {
//                Tasks task = new Tasks();
//                task.setTask_id(rs.getInt("task_id"));
//                task.setTask_name(rs.getString("task_name"));
//                task.setDescription(rs.getString("description"));
//                task.setStart_date(rs.getDate("start_date"));
//                task.setEnd_date(rs.getDate("end_date"));
//                task.setMilestone_id(rs.getInt("milestone_id"));
//                return task;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null; // Return null if no task is found
//    }
//
//

}
