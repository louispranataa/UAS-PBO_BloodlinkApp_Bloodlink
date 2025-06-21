package com.bloodlink.controllers;

import com.bloodlink.App;
import com.bloodlink.dao.HospitalDAO;
import com.bloodlink.models.Hospital;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HospitalRegisterController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField addressField;

    private final HospitalDAO hospitalDAO = new HospitalDAO();

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String address = addressField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setEmail(email);
        hospital.setPassword(password);
        hospital.setAddress(address);

        try {
            boolean success = hospitalDAO.register(hospital);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Hospital registered successfully.");
                App.setRoot("hospital_login");
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration failed. Email might already exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "An error occurred.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            App.setRoot("hospital_login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Hospital Registration");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
