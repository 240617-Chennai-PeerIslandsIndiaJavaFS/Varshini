package org.example.model;

public class TeamMembers {
    private int team_id;
    private int user_id;

    public TeamMembers(){

    }

    public TeamMembers(int team_id, int user_id) {
        this.team_id = team_id;
        this.user_id = user_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
