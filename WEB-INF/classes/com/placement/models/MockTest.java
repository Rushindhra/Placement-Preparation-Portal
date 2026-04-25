package com.placement.models;


import java.sql.Timestamp;

public class MockTest {
    private int id, subjectId, duration, totalMarks;
    private String title, description;
    private boolean active;
    private Timestamp createdAt;
    private String subjectName; // joined

    public MockTest() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int s) { this.subjectId = s; }
    public int getDuration() { return duration; }
    public void setDuration(int d) { this.duration = d; }
    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int m) { this.totalMarks = m; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public boolean isActive() { return active; }
    public void setActive(boolean a) { this.active = a; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String s) { this.subjectName = s; }
}