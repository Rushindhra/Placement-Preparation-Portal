package com.placement.models;



import java.sql.Date;
import java.sql.Timestamp;

public class Job {
    private int id;
    private String title, company, location, type, packageInfo, description, requirements, applyLink;
    private Date deadline;
    private boolean active;
    private Timestamp createdAt;
    private boolean applied; // computed

    public Job() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getCompany() { return company; }
    public void setCompany(String c) { this.company = c; }
    public String getLocation() { return location; }
    public void setLocation(String l) { this.location = l; }
    public String getType() { return type; }
    public void setType(String t) { this.type = t; }
    public String getPackageInfo() { return packageInfo; }
    public void setPackageInfo(String p) { this.packageInfo = p; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getRequirements() { return requirements; }
    public void setRequirements(String r) { this.requirements = r; }
    public String getApplyLink() { return applyLink; }
    public void setApplyLink(String a) { this.applyLink = a; }
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date d) { this.deadline = d; }
    public boolean isActive() { return active; }
    public void setActive(boolean a) { this.active = a; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }
    public boolean isApplied() { return applied; }
    public void setApplied(boolean a) { this.applied = a; }
}