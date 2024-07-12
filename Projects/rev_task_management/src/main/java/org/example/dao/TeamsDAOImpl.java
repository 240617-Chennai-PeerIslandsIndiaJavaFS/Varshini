package org.example.dao;

import org.example.DBConnection;
import org.example.model.Teams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamsDAOImpl implements TeamsDAO {

    private static final Logger logger = LoggerFactory.getLogger(TeamsDAOImpl.class);
    static Connection con;

    public TeamsDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public Teams create(Teams team) {
        String query = "INSERT INTO teams(team_name, manager_id, project_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ptmt.setString(1, team.getTeam_name());
            ptmt.setInt(2, team.getManager_id());
            ptmt.setInt(3, team.getProject_id());
            ptmt.executeUpdate();

            ResultSet rs = ptmt.getGeneratedKeys();
            if (rs.next()) {
                team.setTeam_id(rs.getInt(1));
                logger.info("Created team with ID {}", team.getTeam_id());
            }
            return team;
        } catch (SQLException ex) {
            logger.error("SQLException occurred while creating team: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM teams WHERE team_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            int rowsAffected = ptmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Deleted team with ID {}", id);
                return true;
            } else {
                logger.warn("No team found with ID {} to delete", id);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while deleting team with ID {}: {}", id, ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(String teamName, int id) {
        String query = "UPDATE teams SET team_name = ? WHERE team_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, teamName);
            ptmt.setInt(2, id);
            int rowsAffected = ptmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Updated team name for team with ID {}", id);
                return true;
            } else {
                logger.warn("No team found with ID {} to update", id);
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while updating team name for team with ID {}: {}", id, ex.getMessage());
        }
        return false;
    }

    @Override
    public Teams getId(int id) {
        String query = "SELECT * FROM teams WHERE project_id = ?";
        Teams team = new Teams();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet res = ptmt.executeQuery();
            if (res.next()) {
                team.setTeam_id(res.getInt("team_id"));
                team.setTeam_name(res.getString("team_name"));
                team.setManager_id(res.getInt("manager_id"));
                team.setProject_id(res.getInt("project_id"));
                logger.debug("Fetched team details for team with ID {}", id);
            } else {
                logger.warn("No team found with ID {}", id);
            }
            return team;
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching team with ID {}: {}", id, ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Teams> getAll() {
        String query = "SELECT * FROM teams";
        List<Teams> teamsList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                Teams team = new Teams();
                team.setTeam_id(res.getInt("team_id"));
                team.setTeam_name(res.getString("team_name"));
                team.setManager_id(res.getInt("manager_id"));
                team.setProject_id(res.getInt("project_id"));
                teamsList.add(team);
            }
            if (!teamsList.isEmpty()) {
                logger.info("Fetched {} teams from the database", teamsList.size());
            } else {
                logger.warn("No teams found in the database");
            }
        } catch (SQLException ex) {
            logger.error("SQLException occurred while fetching teams: {}", ex.getMessage());
        }
        return teamsList;
    }
}
