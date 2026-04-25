package com.placement.dao;

import com.placement.models.MockTest;
import com.placement.models.Question;
import com.placement.models.TestResult;
import com.placement.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MockTestDAO {

    // ─── TESTS ──────────────────────────────────────────────────────────────

    public List<MockTest> getAllActiveTests() {
        List<MockTest> list = new ArrayList<>();
        String sql = "SELECT m.*, s.name AS subject_name FROM mock_tests m " +
                     "LEFT JOIN subjects s ON m.subject_id = s.id " +
                     "WHERE m.is_active = TRUE ORDER BY m.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapTest(rs));

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<MockTest> getAllTests() {
        List<MockTest> list = new ArrayList<>();
        String sql = "SELECT m.*, s.name AS subject_name FROM mock_tests m " +
                     "LEFT JOIN subjects s ON m.subject_id = s.id ORDER BY m.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapTest(rs));

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public MockTest getTestById(int id) {
        String sql = "SELECT m.*, s.name AS subject_name FROM mock_tests m " +
                     "LEFT JOIN subjects s ON m.subject_id = s.id WHERE m.id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapTest(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean addTest(MockTest t) {
        String sql = "INSERT INTO mock_tests (title, subject_id, duration, total_marks, description) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setInt(2, t.getSubjectId());
            ps.setInt(3, t.getDuration());
            ps.setInt(4, t.getTotalMarks());
            ps.setString(5, t.getDescription());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteTest(int id) {
        String sql = "DELETE FROM mock_tests WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ─── QUESTIONS ──────────────────────────────────────────────────────────

    public List<Question> getQuestionsByTest(int testId) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE test_id = ? ORDER BY id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, testId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapQuestion(rs));

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addQuestion(Question q) {
        String sql = "INSERT INTO questions (test_id, question, option_a, option_b, option_c, option_d, correct_ans, marks) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, q.getTestId());
            ps.setString(2, q.getQuestion());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, q.getCorrectAns());
            ps.setInt(8, q.getMarks());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteQuestion(int id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ─── RESULTS ────────────────────────────────────────────────────────────

    public boolean saveResult(TestResult r) {
        String sql = "INSERT INTO test_results (user_id, test_id, score, total_marks, time_taken) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getTestId());
            ps.setInt(3, r.getScore());
            ps.setInt(4, r.getTotalMarks());
            ps.setInt(5, r.getTimeTaken());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<TestResult> getResultsByUser(int userId) {
        List<TestResult> list = new ArrayList<>();
        String sql = "SELECT tr.*, m.title AS test_title FROM test_results tr " +
                     "JOIN mock_tests m ON tr.test_id = m.id WHERE tr.user_id = ? " +
                     "ORDER BY tr.attempted_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TestResult r = mapResult(rs);
                r.setTestTitle(rs.getString("test_title"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int countTestsAttempted(int userId) {
        String sql = "SELECT COUNT(DISTINCT test_id) FROM test_results WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private MockTest mapTest(ResultSet rs) throws SQLException {
        MockTest m = new MockTest();
        m.setId(rs.getInt("id"));
        m.setTitle(rs.getString("title"));
        m.setSubjectId(rs.getInt("subject_id"));
        m.setDuration(rs.getInt("duration"));
        m.setTotalMarks(rs.getInt("total_marks"));
        m.setDescription(rs.getString("description"));
        m.setActive(rs.getBoolean("is_active"));
        m.setCreatedAt(rs.getTimestamp("created_at"));
        try { m.setSubjectName(rs.getString("subject_name")); } catch (Exception ignored) {}
        return m;
    }

    private Question mapQuestion(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setId(rs.getInt("id"));
        q.setTestId(rs.getInt("test_id"));
        q.setQuestion(rs.getString("question"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectAns(rs.getString("correct_ans"));
        q.setMarks(rs.getInt("marks"));
        q.setCreatedAt(rs.getTimestamp("created_at"));
        return q;
    }

    private TestResult mapResult(ResultSet rs) throws SQLException {
        TestResult r = new TestResult();
        r.setId(rs.getInt("id"));
        r.setUserId(rs.getInt("user_id"));
        r.setTestId(rs.getInt("test_id"));
        r.setScore(rs.getInt("score"));
        r.setTotalMarks(rs.getInt("total_marks"));
        r.setTimeTaken(rs.getInt("time_taken"));
        r.setAttemptedAt(rs.getTimestamp("attempted_at"));
        return r;
    }
}