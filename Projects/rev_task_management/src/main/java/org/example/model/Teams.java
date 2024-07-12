package org.example.model;

public class Teams {
    private int team_id;
    private String team_name;
    private int project_id;
    private int manager_id;
    public Teams(){

    }

    public Teams(int team_id, String team_name, int project_id, int manager_id) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.project_id = project_id;
        this.manager_id = manager_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }
}
