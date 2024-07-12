package org.example.dao;

import org.example.DBConnection;
import org.example.model.Clients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImp implements ClientDAO<Clients> {
    private static final Logger logger = LoggerFactory.getLogger(ClientDAOImp.class);

    static Connection con;

    public ClientDAOImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public Clients create(Clients client) {
        String query = "INSERT INTO clients(client_name, client_company_name, client_email, client_phone, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, client.getClient_name());
            ptmt.setString(2, client.getClient_company_name());
            ptmt.setString(3, client.getEmail());
            ptmt.setString(4, client.getPhoneNumber());
            ptmt.setTimestamp(5, new java.sql.Timestamp(client.getCreatedAt().getTime()));
            ptmt.setTimestamp(6, new java.sql.Timestamp(client.getUpdatedAt().getTime()));
            ptmt.executeUpdate();

            logger.info("Client created successfully: {}", client);
            return client;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM clients WHERE client_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            int row = ptmt.executeUpdate();
            logger.info("{} row(s) deleted", row);
            return row > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(String client_name, int id) {
        String query = "UPDATE clients SET client_name = ? WHERE client_id = ?";
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setString(1, client_name);
            ptmt.setInt(2, id);
            int row = ptmt.executeUpdate();
            logger.info("{} row(s) updated", row);
            return row > 0;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public Clients getId(int id) {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        Clients client = new Clients();
        try {
            PreparedStatement ptmt = con.prepareStatement(query);
            ptmt.setInt(1, id);
            ResultSet res = ptmt.executeQuery();
            if (res.next()) {
                client.setClient_id(res.getInt("client_id"));
                client.setClient_name(res.getString("client_name"));
                client.setClient_company_name(res.getString("client_company_name"));
                client.setEmail(res.getString("client_email"));
                client.setPhoneNumber(res.getString("client_phone"));
                client.setCreatedAt(res.getTimestamp("created_at"));
                client.setUpdatedAt(res.getTimestamp("updated_at"));
            }
            logger.info("Retrieved client with ID: {}", id);
            return client;
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Clients> getAll() {
        String query = "SELECT * FROM clients";
        List<Clients> clientsList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                Clients client = new Clients();
                client.setClient_id(res.getInt("client_id"));
                client.setClient_name(res.getString("client_name"));
                client.setClient_company_name(res.getString("client_company_name"));
                client.setEmail(res.getString("client_email"));
                client.setPhoneNumber(res.getString("client_phone"));
                client.setCreatedAt(res.getTimestamp("created_at"));
                client.setUpdatedAt(res.getTimestamp("updated_at"));
                clientsList.add(client);
            }
            logger.info("Retrieved all clients");
        } catch (SQLException ex) {
            logger.error("SQLException occurred: {}", ex.getMessage());
        }
        return clientsList;
    }
}