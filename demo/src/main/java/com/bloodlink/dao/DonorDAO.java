package com.bloodlink.dao;

import java.sql.*;

import com.bloodlink.DbUtil;
import com.bloodlink.PasswordUtil;
import com.bloodlink.models.Donor;

public class DonorDAO {

    public boolean register(Donor donor) throws SQLException {
        String sql = "INSERT INTO donor (name, email, password, blood_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, donor.getName());
            ps.setString(2, donor.getEmail());
            ps.setString(3, PasswordUtil.hash(donor.getPassword()));
            ps.setString(4, donor.getBloodType());
            return ps.executeUpdate() > 0;
        }
    }

    public Donor login(String email, String rawPassword) throws SQLException {
        String sql = "SELECT * FROM donor WHERE email = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && PasswordUtil.verify(rawPassword, rs.getString("password"))) {
                Donor d = new Donor();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setEmail(email);
                d.setBloodType(rs.getString("blood_type"));
                return d;
            }
        }
        return null;
    }
}
