package org.example.model;

import java.sql.Date;
import java.time.LocalDate;

public class Users {
    private int user_id;
    private String user_name;
    private Role user_role;
    private String email;
    private String password;
    private String phone;
    private int manager_id;
    private Status status;
    private String specilization;
    private Date date_of_joining;

    public Users(){

    }

    public Users(int user_id, String user_name, Role user_role, String email, String password, String phone, int manager_id, Status status, String specilization, Date date_of_joining) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_role = user_role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.manager_id = manager_id;
        this.status = status;
        this.specilization = specilization;
        this.date_of_joining = date_of_joining;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Role getUser_role() {
        return user_role;
    }

    public void setUser_role(Role user_role) {
        this.user_role = Role.valueOf(String.valueOf(user_role)); // Converts string to enum
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSpecilization() {
        return specilization;
    }

    public void setSpecilization(String specilization) {
        this.specilization = specilization;
    }

    public Date getDate_of_joining() {
        return date_of_joining;
    }

    public void setDate_of_joining(Date date_of_joining) {
        this.date_of_joining = date_of_joining;
    }
}
