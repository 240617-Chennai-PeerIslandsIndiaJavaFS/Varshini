package org.example.model;

import java.util.Date;

public class Clients {
    private int client_id;
    private String client_name;
    private String client_company_name;
    private String email;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;
    public Clients() {

    }

    public Clients(int client_id, String client_name, String client_company_name, String email, String phoneNumber, Date createdAt, Date updatedAt) {
        this.client_id = client_id;
        this.client_name = client_name;
        this.client_company_name = client_company_name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_company_name() {
        return client_company_name;
    }

    public void setClient_company_name(String client_company_name) {
        this.client_company_name = client_company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "client_id=" + client_id +
                ", client_name='" + client_name + '\'' +
                ", client_company_name='" + client_company_name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
