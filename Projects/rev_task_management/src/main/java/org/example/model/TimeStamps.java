package org.example.model;

import java.sql.Timestamp;

public class TimeStamps {

    private int timeline_id;
    private Milestones milestones;
    private int milestone_id;
    private Tasks tasks;
    private int task_id;
    private Timestamp timestamp;

    public TimeStamps() {

    }

    public TimeStamps(int timeline_id, Milestones milestones, Tasks tasks, Timestamp timestamp, int milestone_id,int task_id) {
        this.timeline_id = timeline_id;
        this.milestones = milestones;
        this.tasks = tasks;
        this.timestamp = timestamp;
        this.milestone_id=milestone_id;
        this.task_id=task_id;
    }

    public int getTimeline_id() {
        return timeline_id;
    }

    public void setTimeline_id(int timeline_id) {
        this.timeline_id = timeline_id;
    }

    public Milestones getMilestones() {
        return milestones;
    }

    public void setMilestones(Milestones milestones) {
        this.milestones = milestones;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
