package com.bloodlink.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bloodlink.App;
import com.bloodlink.DbUtil;
import com.bloodlink.dao.HospitalDAO;
import com.bloodlink.models.Hospital;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HospitalLoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private final HospitalDAO hospitalDAO = new HospitalDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter email and password.");
            return;
        }

        if (!email.endsWith(".go.id")) {
            showAlert(Alert.AlertType.WARNING, "Only .go.id emails are allowed for hospital registration.");
            return;
        }

        try {
            Hospital hospital = hospitalDAO.login(email, password);
            if (hospital != null) {
                HospitalDashboardController.setLoggedInHospitalId(hospital.getId());

                // Check low blood supply
                if (isLowBloodSupply(hospital.getId())) {
                    showAlert(Alert.AlertType.WARNING,
                            "⚠ Some blood types have less than 3 units. Please review your blood stock.");
                }

                showAlert(Alert.AlertType.INFORMATION, "Login successful! Welcome, " + hospital.getName());
                App.setRoot("hospital_dashboard");
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid email or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Login failed.");
        }
    }

    private boolean isLowBloodSupply(int hospitalId) {
        String query = "SELECT COUNT(*) FROM hospital_blood_supply WHERE hospital_id = ? AND units < 3";

        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, hospitalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void handleGoToRegister() {
        try {
            App.setRoot("hospital_register");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToDonorLogin() {
        try {
            App.setRoot("donor_login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Hospital Login");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
