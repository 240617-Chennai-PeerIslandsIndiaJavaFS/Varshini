package org.example.service;

import org.example.dao.ClientDAOImp;
import org.example.model.Clients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static org.example.service.Validation.*;
import static org.example.service.Validation.isValidEmail;

public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private static Scanner sc = new Scanner(System.in);
    private static ClientDAOImp clientDAO = new ClientDAOImp();

    public static void addClient() {
        logger.info("Starting client creation process.");

        Clients client = new Clients();

        System.out.print("Enter Client Name: ");
        client.setClient_name(getValidClientName(sc));


        System.out.print("Enter Company Name: ");
        client.setClient_company_name(sc.nextLine());

        String newEmail;
        do {
            System.out.print("Enter email: ");
            newEmail = sc.nextLine();
            client.setEmail(newEmail);
            if (!isValidEmail(newEmail)) {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        } while (!isValidEmail(newEmail));


        String phone;
        do {
            System.out.print("Enter phone: ");
            phone = sc.nextLine();
            client.setPhoneNumber(phone);
            if (!isValidPhoneNumber(phone)) {
                System.out.println("Invalid phone number format. Please enter a valid international phone number starting with '+'.");
            }
        } while (!isValidPhoneNumber(phone));

        client.setCreatedAt(new java.util.Date());
        client.setUpdatedAt(new java.util.Date());

        Clients addedClient = clientDAO.create(client);
        if (addedClient != null) {
            logger.info("Client added successfully. Client ID: {}", addedClient.getClient_id());
            System.out.println("Client added successfully.");
        } else {
            logger.error("Failed to add client.");
            System.out.println("Failed to add client.");
        }
    }

    public static void updateClient() {
        logger.info("Starting client update process.");

        System.out.print("Enter Client ID to update: ");
        int clientId = getValidUserId(sc);
        sc.nextLine();

        System.out.print("Enter new Client Name: ");
        String newClientName = getValidClientName(sc);

        boolean success = clientDAO.update(newClientName, clientId);
        if (success) {
            logger.info("Client updated successfully. Client ID: {}", clientId);
            System.out.println("Client updated successfully.");
        } else {
            logger.error("Failed to update client. Client ID: {}", clientId);
            System.out.println("Failed to update client.");
        }
    }

    public static void deleteClient() {
        logger.info("Starting client deletion process.");

        System.out.print("Enter Client ID to delete: ");
        int clientId = getValidUserId(sc);
        sc.nextLine();

        boolean success = clientDAO.delete(clientId);
        if (success) {
            logger.info("Client deleted successfully. Client ID: {}", clientId);
            System.out.println("Client deleted successfully.");
        } else {
            logger.error("Failed to delete client. Client ID: {}", clientId);
            System.out.println("Failed to delete client.");
        }
    }

    public static void getIdClient() {
        logger.info("Starting client retrieval process by ID.");

        System.out.print("Enter Client ID to retrieve: ");
        int clientId =getValidUserId(sc);
        sc.nextLine();

        Clients client = clientDAO.getId(clientId);
        if (client != null) {
            logger.info("Client details retrieved successfully. Client ID: {}", clientId);
            System.out.println("Client Details:");
            System.out.println("ID: " + client.getClient_id());
            System.out.println("Name: " + client.getClient_name());
            System.out.println("Company Name: " + client.getClient_company_name());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Phone Number: " + client.getPhoneNumber());
            System.out.println("Created At: " + client.getCreatedAt());
            System.out.println("Updated At: " + client.getUpdatedAt());
        } else {
            logger.warn("Client not found. Client ID: {}", clientId);
            System.out.println("Client not found.");
        }
    }
}
