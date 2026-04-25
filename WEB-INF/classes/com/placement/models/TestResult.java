package com.placement.models;



import java.sql.Timestamp;

public class TestResult {
    private int id, userId, testId, score, totalMarks, timeTaken;
    private Timestamp attemptedAt;
    private String testTitle; // joined

    public TestResult() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int u) { this.userId = u; }
    public int getTestId() { return testId; }
    public void setTestId(int t) { this.testId = t; }
    public int getScore() { return score; }
    public void setScore(int s) { this.score = s; }
    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int m) { this.totalMarks = m; }
    public int getTimeTaken() { return timeTaken; }
    public void setTimeTaken(int t) { this.timeTaken = t; }
    public Timestamp getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(Timestamp t) { this.attemptedAt = t; }
    public String getTestTitle() { return testTitle; }
    public void setTestTitle(String t) { this.testTitle = t; }
    public int getPercentage() {
        return totalMarks == 0 ? 0 : (int) ((score * 100.0) / totalMarks);
    }
}