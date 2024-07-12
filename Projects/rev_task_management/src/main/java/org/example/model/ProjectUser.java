package org.example.model;

public class ProjectUser {
    private int project_user_id;
    private Users users;
    private  int user_id;
    private Projects projects;
    private int project_id;
    public ProjectUser(){

    }
    public ProjectUser(int project_user_id, Users users, Projects projects,int user_id, int project_id) {
        this.project_user_id = project_user_id;
        this.users = users;
        this.projects = projects;
        this.user_id=user_id;
        this.project_id=project_id;
    }

    public int getProject_user_id() {
        return project_user_id;
    }

    public void setProject_user_id(int project_user_id) {
        this.project_user_id = project_user_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
