package com.bloodlink.controllers;

import com.bloodlink.App;
import com.bloodlink.dao.DonorDAO;
import com.bloodlink.models.Donor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class DonorLoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private final DonorDAO donorDAO = new DonorDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Please enter both email and password.");
            return;
        }

        try {
            Donor donor = donorDAO.login(email, password);
            if (donor != null) {
                showAlert(AlertType.INFORMATION, "Login successful! Welcome, " + donor.getName());
                DonorDashboardController.loggedInDonorId = donor.getId();
                App.setRoot("donor_dashboard");
            } else {
                showAlert(AlertType.ERROR, "Login failed. Invalid email or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "An error occurred during login.");
        }
    }

    @FXML
    private void handleGoToRegister() {
        try {
            App.setRoot("donor_register");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToHospitalLogin() {
        try {
            App.setRoot("hospital_login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Donor Login");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
