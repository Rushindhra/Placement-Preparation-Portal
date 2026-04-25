package com.placement.dao;

import com.placement.models.Job;
import com.placement.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    public List<Job> getAllActiveJobs(int userId) {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT j.*, " +
                     "EXISTS(SELECT 1 FROM job_applications a WHERE a.job_id=j.id AND a.user_id=?) AS applied " +
                     "FROM jobs j WHERE j.is_active=TRUE ORDER BY j.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Job j = mapRow(rs);
                j.setApplied(rs.getBoolean("applied"));
                list.add(j);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Job> searchJobs(String keyword, String type, int userId) {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT j.*, " +
                     "EXISTS(SELECT 1 FROM job_applications a WHERE a.job_id=j.id AND a.user_id=?) AS applied " +
                     "FROM jobs j WHERE j.is_active=TRUE " +
                     "AND (j.title LIKE ? OR j.company LIKE ? OR j.location LIKE ?) " +
                     (type != null && !type.isEmpty() ? "AND j.type = ? " : "") +
                     "ORDER BY j.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String kw = "%" + (keyword == null ? "" : keyword.trim()) + "%";
            ps.setInt(1, userId);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            if (type != null && !type.isEmpty()) ps.setString(5, type);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Job j = mapRow(rs);
                j.setApplied(rs.getBoolean("applied"));
                list.add(j);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Job> getAllJobsAdmin() {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT * FROM jobs ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Job getJobById(int id) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean addJob(Job j) {
        String sql = "INSERT INTO jobs (title, company, location, type, package, description, requirements, apply_link, deadline) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, j.getTitle());
            ps.setString(2, j.getCompany());
            ps.setString(3, j.getLocation());
            ps.setString(4, j.getType());
            ps.setString(5, j.getPackageInfo());
            ps.setString(6, j.getDescription());
            ps.setString(7, j.getRequirements());
            ps.setString(8, j.getApplyLink());
            ps.setDate(9, j.getDeadline());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateJob(Job j) {
        String sql = "UPDATE jobs SET title=?,company=?,location=?,type=?,package=?,description=?,requirements=?,apply_link=?,deadline=?,is_active=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, j.getTitle());
            ps.setString(2, j.getCompany());
            ps.setString(3, j.getLocation());
            ps.setString(4, j.getType());
            ps.setString(5, j.getPackageInfo());
            ps.setString(6, j.getDescription());
            ps.setString(7, j.getRequirements());
            ps.setString(8, j.getApplyLink());
            ps.setDate(9, j.getDeadline());
            ps.setBoolean(10, j.isActive());
            ps.setInt(11, j.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteJob(int id) {
        String sql = "DELETE FROM jobs WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean applyForJob(int userId, int jobId) {
        String sql = "INSERT IGNORE INTO job_applications (user_id, job_id) VALUES (?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, jobId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int countActiveJobs() {
        String sql = "SELECT COUNT(*) FROM jobs WHERE is_active=TRUE";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Job mapRow(ResultSet rs) throws SQLException {
        Job j = new Job();
        j.setId(rs.getInt("id"));
        j.setTitle(rs.getString("title"));
        j.setCompany(rs.getString("company"));
        j.setLocation(rs.getString("location"));
        j.setType(rs.getString("type"));
        j.setPackageInfo(rs.getString("package"));
        j.setDescription(rs.getString("description"));
        j.setRequirements(rs.getString("requirements"));
        j.setApplyLink(rs.getString("apply_link"));
        j.setDeadline(rs.getDate("deadline"));
        j.setActive(rs.getBoolean("is_active"));
        j.setCreatedAt(rs.getTimestamp("created_at"));
        return j;
    }
}