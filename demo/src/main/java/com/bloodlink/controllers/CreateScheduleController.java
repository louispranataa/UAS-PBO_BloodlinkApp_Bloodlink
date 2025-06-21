package com.bloodlink.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.bloodlink.App;
import com.bloodlink.DbUtil;
import com.bloodlink.dao.DonationScheduleDAO;
import com.bloodlink.models.DonationSchedule;

public class CreateScheduleController {

    @FXML
    private CheckBox infectiousDisease;
    @FXML
    private CheckBox chronicIllness;
    @FXML
    private CheckBox pregnantOrPostpartum;
    @FXML
    private CheckBox recentSurgery;
    @FXML
    private CheckBox bloodPressureIssue;
    @FXML
    private CheckBox recentDonation;
    @FXML
    private Button continueButton;

    @FXML
    private VBox scheduleForm;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField locationField;
    @FXML
    private TextField hospitalField;
    @FXML
    private ComboBox<String> hospitalComboBox;

    private Map<String, Integer> hospitalMap = new HashMap<>(); // name → id

    private final DonationScheduleDAO dao = new DonationScheduleDAO();

    private String generateCode() {
        return "BLD-" + System.currentTimeMillis();
    }

    @FXML
    private void initialize() {
        try {
            Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM hospital");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                hospitalComboBox.getItems().add(name);
                hospitalMap.put(name, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleContinue() {
        boolean allHealthy = infectiousDisease.isSelected() &&
                chronicIllness.isSelected() &&
                pregnantOrPostpartum.isSelected() &&
                recentSurgery.isSelected() &&
                bloodPressureIssue.isSelected() &&
                recentDonation.isSelected();

        if (!allHealthy) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Health Check Incomplete");
            alert.setHeaderText("You may not be eligible to donate.");
            alert.setContentText("Please confirm all health conditions to continue.");
            alert.showAndWait();
        } else {
            scheduleForm.setVisible(true);
            scheduleForm.setManaged(true);
        }
    }

    @FXML
    private void handleSubmit() {
        LocalDate date = datePicker.getValue();
        String hospital = hospitalComboBox.getValue();
        int hospitalId = hospitalMap.get(hospital);

        if (date == null || hospital == null || hospital.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a date and hospital.");
            return;
        }

        int donorId = DonorDashboardController.loggedInDonorId;

        // Step 1: Check latest donation date
        try (Connection conn = DbUtil.getConnection()) {
            String query = "SELECT MAX(date) AS last_date FROM donation_schedule WHERE donor_id = ? AND is_done = 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getDate("last_date") != null) {
                LocalDate lastDonation = rs.getDate("last_date").toLocalDate();
                if (date.isBefore(lastDonation.plusWeeks(8))) {
                    showAlert(Alert.AlertType.WARNING,
                            "You must wait at least 8 weeks between donations.\nLast donation: " + lastDonation +
                                    "\nEarliest next donation: " + lastDonation.plusWeeks(8));
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to validate last donation.");
            return;
        }

        // Step 2: Continue inserting
        String code = generateCode();

        DonationSchedule ds = new DonationSchedule();
        ds.setDate(date);
        ds.setHospitalId(hospitalId);
        ds.setDonorId(donorId);
        ds.setCode(code);

        try {
            dao.insert(ds);
            showAlert(Alert.AlertType.INFORMATION, "Schedule created successfully!\n\nYour Code: " + code);
            App.setRoot("donor_dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to create schedule.");
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Schedule");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
