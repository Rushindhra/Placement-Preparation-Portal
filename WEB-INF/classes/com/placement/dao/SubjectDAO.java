package com.placement.dao;


import com.placement.models.Subject;
import com.placement.models.Topic;
import com.placement.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // ─── SUBJECTS ───────────────────────────────────────────────────────────

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects ORDER BY name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapSubject(rs));

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** Subjects with per-user completion progress. */
    public List<Subject> getAllSubjectsWithProgress(int userId) {
        List<Subject> list = getAllSubjects();
        for (Subject s : list) {
            int[] counts = getTopicCounts(s.getId(), userId);
            s.setTotalTopics(counts[0]);
            s.setCompletedTopics(counts[1]);
        }
        return list;
    }

    private int[] getTopicCounts(int subjectId, int userId) {
        int[] counts = {0, 0};
        String sql = "SELECT COUNT(t.id) AS total, " +
                     "SUM(CASE WHEN p.completed = TRUE THEN 1 ELSE 0 END) AS done " +
                     "FROM topics t LEFT JOIN user_topic_progress p " +
                     "ON t.id = p.topic_id AND p.user_id = ? WHERE t.subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                counts[0] = rs.getInt("total");
                counts[1] = rs.getInt("done");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return counts;
    }

    public Subject getSubjectById(int id) {
        String sql = "SELECT * FROM subjects WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapSubject(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean addSubject(Subject s) {
        String sql = "INSERT INTO subjects (name, description, icon, color) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setString(3, s.getIcon());
            ps.setString(4, s.getColor());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateSubject(Subject s) {
        String sql = "UPDATE subjects SET name=?, description=?, icon=?, color=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setString(3, s.getIcon());
            ps.setString(4, s.getColor());
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteSubject(int id) {
        String sql = "DELETE FROM subjects WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ─── TOPICS ─────────────────────────────────────────────────────────────

    public List<Topic> getTopicsBySubject(int subjectId, int userId) {
        List<Topic> list = new ArrayList<>();
        String sql = "SELECT t.*, " +
                     "COALESCE(p.completed, FALSE) AS completed " +
                     "FROM topics t " +
                     "LEFT JOIN user_topic_progress p ON t.id = p.topic_id AND p.user_id = ? " +
                     "WHERE t.subject_id = ? ORDER BY t.id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Topic t = mapTopic(rs);
                t.setCompleted(rs.getBoolean("completed"));
                list.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Topic> getAllTopics() {
        List<Topic> list = new ArrayList<>();
        String sql = "SELECT * FROM topics ORDER BY subject_id, id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapTopic(rs));

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addTopic(Topic t) {
        String sql = "INSERT INTO topics (subject_id, title, content, resource_url, difficulty) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, t.getSubjectId());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getContent());
            ps.setString(4, t.getResourceUrl());
            ps.setString(5, t.getDifficulty());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteTopic(int id) {
        String sql = "DELETE FROM topics WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /** Toggle topic completion for a user. */
    public boolean toggleTopicProgress(int userId, int topicId, boolean completed) {
        String sql = "INSERT INTO user_topic_progress (user_id, topic_id, completed, completed_at) " +
                     "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE completed=?, completed_at=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            Timestamp now = completed ? new Timestamp(System.currentTimeMillis()) : null;
            ps.setInt(1, userId);
            ps.setInt(2, topicId);
            ps.setBoolean(3, completed);
            ps.setTimestamp(4, now);
            ps.setBoolean(5, completed);
            ps.setTimestamp(6, now);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /** Total topics completed across all subjects for a user. */
    public int countCompletedTopics(int userId) {
        String sql = "SELECT COUNT(*) FROM user_topic_progress WHERE user_id=? AND completed=TRUE";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Subject mapSubject(ResultSet rs) throws SQLException {
        Subject s = new Subject();
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setDescription(rs.getString("description"));
        s.setIcon(rs.getString("icon"));
        s.setColor(rs.getString("color"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        return s;
    }

    private Topic mapTopic(ResultSet rs) throws SQLException {
        Topic t = new Topic();
        t.setId(rs.getInt("id"));
        t.setSubjectId(rs.getInt("subject_id"));
        t.setTitle(rs.getString("title"));
        t.setContent(rs.getString("content"));
        t.setResourceUrl(rs.getString("resource_url"));
        t.setDifficulty(rs.getString("difficulty"));
        t.setCreatedAt(rs.getTimestamp("created_at"));
        return t;
    }
}