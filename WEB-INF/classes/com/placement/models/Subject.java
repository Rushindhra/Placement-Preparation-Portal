package com.placement.models;


import java.sql.Timestamp;

// ─────────────────── Subject ───────────────────
public class Subject {
    private int id;
    private String name, description, icon, color;
    private Timestamp createdAt;
    // computed fields (not in DB)
    private int totalTopics;
    private int completedTopics;

    public Subject() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String desc) { this.description = desc; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }
    public int getTotalTopics() { return totalTopics; }
    public void setTotalTopics(int t) { this.totalTopics = t; }
    public int getCompletedTopics() { return completedTopics; }
    public void setCompletedTopics(int c) { this.completedTopics = c; }
    public int getProgressPercent() {
        return totalTopics == 0 ? 0 : (int) ((completedTopics * 100.0) / totalTopics);
    }
}