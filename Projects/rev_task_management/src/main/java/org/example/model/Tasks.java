package org.example.model;


import java.sql.Date;
import java.sql.Timestamp;

public class Tasks {
    private int task_id;
    private Projects projects;
    private String task_name;
    private Projects project;
    private int project_id;
    private double percentage;
    private Date start_date;
    private Date end_date;
    private Milestones milestone;
    private  int milestone_id;
    private int user_id;
    private Users user;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Clients clients;

    public Tasks(){

    }

    public Tasks(int task_id,Projects projects, String task_name, Projects project, double percentage, Date start_date, Date end_date, Milestones milestone, Users user, String description, Timestamp createdAt, Timestamp updatedAt, Clients clients,int project_id,int milestone_id,int user_id) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.project = project;
        this.percentage = percentage;
        this.start_date = start_date;
        this.end_date = end_date;
        this.milestone = milestone;
        this.user = user;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.clients = clients;
        this.project = projects;
        this.project_id = project_id;
        this.milestone_id = milestone_id;
        this.user_id= user_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
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

    public Milestones getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestones milestone) {
        this.milestone = milestone;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
