package com.placement.models;



import java.sql.Timestamp;

public class Topic {
    private int id, subjectId;
    private String title, content, resourceUrl, difficulty;
    private Timestamp createdAt;
    private boolean completed; // from user_topic_progress

    public Topic() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int s) { this.subjectId = s; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getContent() { return content; }
    public void setContent(String c) { this.content = c; }
    public String getResourceUrl() { return resourceUrl; }
    public void setResourceUrl(String u) { this.resourceUrl = u; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String d) { this.difficulty = d; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean c) { this.completed = c; }
}
