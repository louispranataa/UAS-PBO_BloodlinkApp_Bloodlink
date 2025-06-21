package com.bloodlink.dao;

import java.sql.*;
import com.bloodlink.DbUtil;
import com.bloodlink.PasswordUtil;

import com.bloodlink.models.Hospital;

public class HospitalDAO {

    public boolean register(Hospital hospital) throws SQLException {
        String sql = "INSERT INTO hospital (name, email, password, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hospital.getName());
            ps.setString(2, hospital.getEmail());
            ps.setString(3, PasswordUtil.hash(hospital.getPassword()));
            ps.setString(4, hospital.getAddress());
            return ps.executeUpdate() > 0;
        }
    }

    public Hospital login(String email, String rawPassword) throws SQLException {
        String sql = "SELECT * FROM hospital WHERE email = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && PasswordUtil.verify(rawPassword, rs.getString("password"))) {
                Hospital hospital = new Hospital();
                hospital.setId(rs.getInt("id"));
                hospital.setName(rs.getString("name"));
                hospital.setEmail(email);
                hospital.setAddress(rs.getString("address"));
                return hospital;
            }
        }
        return null;
    }
}
