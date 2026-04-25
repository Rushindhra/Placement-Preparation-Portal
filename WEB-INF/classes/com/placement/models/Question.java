package com.placement.models;


import java.sql.Timestamp;

// ─────────────────── Question ───────────────────
public class Question {
    private int id, testId, marks;
    private String question, optionA, optionB, optionC, optionD, correctAns;
    private Timestamp createdAt;

    public Question() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTestId() { return testId; }
    public void setTestId(int t) { this.testId = t; }
    public int getMarks() { return marks; }
    public void setMarks(int m) { this.marks = m; }
    public String getQuestion() { return question; }
    public void setQuestion(String q) { this.question = q; }
    public String getOptionA() { return optionA; }
    public void setOptionA(String o) { this.optionA = o; }
    public String getOptionB() { return optionB; }
    public void setOptionB(String o) { this.optionB = o; }
    public String getOptionC() { return optionC; }
    public void setOptionC(String o) { this.optionC = o; }
    public String getOptionD() { return optionD; }
    public void setOptionD(String o) { this.optionD = o; }
    public String getCorrectAns() { return correctAns; }
    public void setCorrectAns(String c) { this.correctAns = c; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }
}
