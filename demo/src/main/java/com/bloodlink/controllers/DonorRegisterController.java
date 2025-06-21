package com.bloodlink.controllers;

import com.bloodlink.App;
import com.bloodlink.dao.DonorDAO;
import com.bloodlink.models.Donor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class DonorRegisterController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> bloodTypeBox;

    private final DonorDAO donorDAO = new DonorDAO();

    @FXML
    public void initialize() {
        bloodTypeBox.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String bloodType = bloodTypeBox.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || bloodType == null) {
            showAlert(AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        Donor donor = new Donor();
        donor.setName(name);
        donor.setEmail(email);
        donor.setPassword(password);
        donor.setBloodType(bloodType);

        try {
            boolean success = donorDAO.register(donor);
            if (success) {
                showAlert(AlertType.INFORMATION, "Registration successful!");
                App.setRoot("donor_login");
            } else {
                showAlert(AlertType.ERROR, "Registration failed. Email might already be used.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "An error occurred during registration.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            App.setRoot("donor_login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Donor Registration");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
