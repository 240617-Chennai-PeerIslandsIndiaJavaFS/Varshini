package org.example.dao;

import org.example.DBConnection;
import org.example.model.Milestones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MilestoneDAOImp implements MilestoneDAO<Milestones> {
    private static final Logger logger = LoggerFactory.getLogger(MilestoneDAOImp.class);

    static Connection con;

    public MilestoneDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public Milestones create(Milestones milestone) {
        String query = "INSERT INTO milestones(milestone_name, milestone_description, created_at, updated_at) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ptmt.setString(1, milestone.getMilestone_name());
            ptmt.setString(2, milestone.getMilestone_description());
            ptmt.setTimestamp(3, milestone.getCreated_at());
            ptmt.setTimestamp(4, milestone.getUpdated_at());
            ptmt.executeUpdate();

            ResultSet rs = ptmt.getGeneratedKeys();
            if (rs.next()) {
                milestone.setMilestone_id(rs.getInt(1));
            }
            logger.info("Milestone created successfully: {}", milestone);
            return milestone;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM milestones WHERE milestone_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            int row = ptmt.executeUpdate();
            logger.info("{} row(s) deleted", row);
            return row > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Milestones milestone) {
        String query = "UPDATE milestones SET milestone_name = ?, milestone_description = ?, updated_at = ? WHERE milestone_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, milestone.getMilestone_name());
            ptmt.setString(2, milestone.getMilestone_description());
            ptmt.setTimestamp(3, milestone.getUpdated_at());
            ptmt.setInt(4, milestone.getMilestone_id());
            int row = ptmt.executeUpdate();
            logger.info("{} row(s) updated", row);
            return row > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public Milestones getId(int id) {
        String query = "SELECT * FROM milestones WHERE milestone_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                Milestones milestone = new Milestones();
                milestone.setMilestone_id(rs.getInt("milestone_id"));
                milestone.setMilestone_name(rs.getString("milestone_name"));
                milestone.setMilestone_description(rs.getString("milestone_description"));
                milestone.setCreated_at(rs.getTimestamp("created_at"));
                milestone.setUpdated_at(rs.getTimestamp("updated_at"));
                logger.info("Retrieved milestone with ID: {}", id);
                return milestone;
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Milestones> getAll() {
        String query = "SELECT * FROM milestones";
        List<Milestones> milestonesList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Milestones milestone = new Milestones();
                milestone.setMilestone_id(rs.getInt("milestone_id"));
                milestone.setMilestone_name(rs.getString("milestone_name"));
                milestone.setMilestone_description(rs.getString("milestone_description"));
                milestone.setCreated_at(rs.getTimestamp("created_at"));
                milestone.setUpdated_at(rs.getTimestamp("updated_at"));
                milestonesList.add(milestone);
            }
            logger.info("Retrieved all milestones");
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return milestonesList;
    }
}