package org.example.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Projects {
    private int project_id;
    private Clients clients;
    private String project_name;
    private String description;
    private Date start_date;
    private Date end_date;
    private float percentage_left;
    private Users users;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int manager_id;
    private int client_id;

    public Projects(int project_id, Clients clients, String project_name, String description, Date start_date, Date end_date, float percentage_left, Users users, Timestamp created_at, Timestamp updated_at,int manager_id,int client_id) {
        this.project_id = project_id;
        this.clients = clients;
        this.project_name = project_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.percentage_left = percentage_left;
        this.users = users;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.manager_id=manager_id;
        this.client_id = client_id;
    }

    public Projects() {

    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public float getPercentage_left() {
        return percentage_left;
    }

    public void setPercentage_left(float percentage_left) {
        this.percentage_left = percentage_left;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
