package org.example.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Milestones {
    private int milestone_id;
    private String milestone_name;
    private String milestone_description;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Milestones() {

    }

    public Milestones(int milestone_id, String milestone_name, String milestone_description, Timestamp created_at, Timestamp updated_at) {
        this.milestone_id = milestone_id;
        this.milestone_name = milestone_name;
        this.milestone_description = milestone_description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getMilestone_name() {
        return milestone_name;
    }

    public void setMilestone_name(String milestone_name) {
        this.milestone_name = milestone_name;
    }

    public String getMilestone_description() {
        return milestone_description;
    }

    public void setMilestone_description(String milestone_description) {
        this.milestone_description = milestone_description;
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
}
