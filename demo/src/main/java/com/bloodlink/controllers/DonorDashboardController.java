package com.bloodlink.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.bloodlink.App;
import com.bloodlink.DbUtil;
import com.bloodlink.dao.DonationScheduleDAO;
import com.bloodlink.models.DonationSchedule;

public class DonorDashboardController {

    @FXML
    private TableView<DonationSchedule> tableView;
    @FXML
    private TableColumn<DonationSchedule, LocalDate> colDate;
    @FXML
    private TableColumn<DonationSchedule, String> colHospital;
    @FXML
    private TableColumn<DonationSchedule, String> colCode;
    @FXML
    private Label countdownLabel;

    private final ObservableList<DonationSchedule> scheduleList = FXCollections.observableArrayList();
    private final DonationScheduleDAO dao = new DonationScheduleDAO();

    public static int loggedInDonorId;

    public static void setLoggedInDonorId(int id) {
        loggedInDonorId = id;
    }

    private void updateCountdown() {
        try (Connection conn = DbUtil.getConnection()) {
            String query = "SELECT MAX(date) AS last_date FROM donation_schedule WHERE donor_id = ? AND is_done = 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInDonorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getDate("last_date") != null) {
                LocalDate lastDonation = rs.getDate("last_date").toLocalDate();
                LocalDate nextEligible = lastDonation.plusWeeks(8);
                long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), nextEligible);

                if (daysLeft > 0) {
                    countdownLabel.setText("You can donate again in " + daysLeft + " day(s).");
                } else {
                    countdownLabel.setText("You're eligible to donate now!");
                }
            } else {
                countdownLabel.setText("You haven't donated yet. You're eligible!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            countdownLabel.setText("Unable to fetch donation history.");
        }
    }

    @FXML
    private void initialize() {
        colDate.setCellValueFactory(
                data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDate()));
        colHospital.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHospitalName()));
        colCode.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
        loadSchedules();
        updateCountdown();
    }

    private void loadSchedules() {
        try {
            scheduleList.setAll(dao.findByDonorId(loggedInDonorId));
            tableView.setItems(scheduleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateSchedule() {
        try {
            App.setRoot("create_schedule");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
