package com.bloodlink.dao;

import java.sql.*;

import com.bloodlink.DbUtil;

public class HospitalBloodSupplyDAO {

    public void addOrUpdateSupply(int hospitalId, String bloodType, int units) throws SQLException {
        String checkQuery = "SELECT id FROM hospital_blood_supply WHERE hospital_id = ? AND blood_type = ?";
        String updateQuery = "UPDATE hospital_blood_supply SET units = units + ? WHERE hospital_id = ? AND blood_type = ?";
        String insertQuery = "INSERT INTO hospital_blood_supply (hospital_id, blood_type, units) VALUES (?, ?, ?)";

        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, hospitalId);
                checkStmt.setString(2, bloodType);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Record exists, update it
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, units);
                        updateStmt.setInt(2, hospitalId);
                        updateStmt.setString(3, bloodType);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Insert new record
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, hospitalId);
                        insertStmt.setString(2, bloodType);
                        insertStmt.setInt(3, units);
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
    }
}
