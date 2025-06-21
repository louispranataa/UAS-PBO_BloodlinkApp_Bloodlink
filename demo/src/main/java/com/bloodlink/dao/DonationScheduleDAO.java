package com.bloodlink.dao;

import java.sql.*;
import java.util.*;

import com.bloodlink.DbUtil;
import com.bloodlink.models.BloodSupply;
import com.bloodlink.models.DonationSchedule;

public class DonationScheduleDAO {

    public List<DonationSchedule> findByDonorId(int donorId) throws SQLException {
        List<DonationSchedule> schedules = new ArrayList<>();
        String query = "SELECT * " +
                "FROM donation_schedule ds " +
                "JOIN hospital h ON ds.hospital_id = h.id " +
                "WHERE ds.donor_id = ?";

        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DonationSchedule schedule = new DonationSchedule();
                schedule.setId(rs.getInt("id"));
                schedule.setHospitalId(rs.getInt("hospital_id"));
                schedule.setDonorId(rs.getInt("donor_id"));
                schedule.setDate(rs.getDate("date").toLocalDate());
                schedule.setHospitalName(rs.getString("name"));
                schedule.setCode(rs.getString("code"));
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    public void insert(DonationSchedule ds) throws SQLException {
        String sql = "INSERT INTO donation_schedule (donor_id, date, hospital_id, code) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ds.getDonorId());
            ps.setDate(2, java.sql.Date.valueOf(ds.getDate()));
            ps.setInt(3, ds.getHospitalId());
            ps.setString(4, ds.getCode());
            ps.executeUpdate();
        }
    }

    public List<DonationSchedule> findByHospitalId(int hospitalId) throws SQLException {
        List<DonationSchedule> schedules = new ArrayList<>();

        String query = "SELECT ds.id, ds.date, ds.code, ds.is_done, d.name AS donor_name, d.blood_type, h.name AS hospital_name FROM donation_schedule ds JOIN donor d ON ds.donor_id = d.id JOIN hospital h ON ds.hospital_id = h.id WHERE ds.hospital_id = ? ORDER BY ds.date DESC";

        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, hospitalId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DonationSchedule ds = new DonationSchedule();
                ds.setId(rs.getInt("id"));
                ds.setDate(rs.getDate("date").toLocalDate());
                ds.setCode(rs.getString("code"));
                ds.setIsDone(rs.getBoolean("is_done"));
                ds.setDonorName(rs.getString("donor_name"));
                ds.setBloodType(rs.getString("blood_type"));
                ds.setHospitalName(rs.getString("hospital_name"));

                schedules.add(ds);
            }
        }

        return schedules;
    }

    public void markAsComplete(int scheduleId, int unitsDonated) throws SQLException {
        String updateSchedule = "UPDATE donation_schedule SET is_done = 1 WHERE id = ?";
        String updateSupply = "UPDATE hospital_blood_supply SET units = units + ? WHERE hospital_id = ? AND blood_type = ?";
        String insertSupply = "INSERT INTO hospital_blood_supply (hospital_id, blood_type, units) VALUES (?, ?, ?)";

        Connection conn = DbUtil.getConnection();
        try {
            conn.setAutoCommit(false); // Start transaction

            // Get hospital_id and blood_type from schedule
            String query = "SELECT hospital_id, donor_id FROM donation_schedule WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, scheduleId);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                conn.rollback();
                return;
            }

            int hospitalId = rs.getInt("hospital_id");
            int donorId = rs.getInt("donor_id");

            // Get blood type from donor
            PreparedStatement donorStmt = conn.prepareStatement("SELECT blood_type FROM donor WHERE id = ?");
            donorStmt.setInt(1, donorId);
            ResultSet donorRs = donorStmt.executeQuery();
            if (!donorRs.next()) {
                conn.rollback();
                return;
            }

            String bloodType = donorRs.getString("blood_type");

            // Update schedule
            PreparedStatement completeStmt = conn.prepareStatement(updateSchedule);
            completeStmt.setInt(1, scheduleId);
            completeStmt.executeUpdate();

            // Try to update supply
            PreparedStatement updateStmt = conn.prepareStatement(updateSupply);
            updateStmt.setInt(1, unitsDonated);
            updateStmt.setInt(2, hospitalId);
            updateStmt.setString(3, bloodType);
            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected == 0) {
                // Insert if not exist
                PreparedStatement insertStmt = conn.prepareStatement(insertSupply);
                insertStmt.setInt(1, hospitalId);
                insertStmt.setString(2, bloodType);
                insertStmt.setInt(3, unitsDonated);
                insertStmt.executeUpdate();
                insertStmt.close();
            }

            // Clean up
            updateStmt.close();
            completeStmt.close();
            donorStmt.close();
            stmt.close();

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            conn.rollback(); // Rollback on error
            throw e;
        } finally {
            conn.setAutoCommit(true); // Restore default
            conn.close();
        }
    }

    public List<BloodSupply> getBloodSupplyByHospitalId(int hospitalId) throws SQLException {
        List<BloodSupply> supplies = new ArrayList<>();
        String query = "SELECT blood_type, units FROM hospital_blood_supply WHERE hospital_id = ?";

        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, hospitalId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String type = rs.getString("blood_type");
                int units = rs.getInt("units");
                supplies.add(new BloodSupply(type, units));
            }
        }
        return supplies;
    }

}
