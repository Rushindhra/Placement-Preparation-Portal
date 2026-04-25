package com.placement.models;


import java.sql.Date;
import java.sql.Timestamp;

public class Task {
    private int id, userId;
    private String title, description, priority;
    private Date dueDate;
    private boolean completed;
    private Timestamp createdAt, updatedAt;

    public Task() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int u) { this.userId = u; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getPriority() { return priority; }
    public void setPriority(String p) { this.priority = p; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date d) { this.dueDate = d; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean c) { this.completed = c; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp t) { this.updatedAt = t; }
}
