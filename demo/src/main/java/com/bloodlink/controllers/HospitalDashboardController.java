package com.bloodlink.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Optional;

import com.bloodlink.dao.DonationScheduleDAO;
import com.bloodlink.models.DonationSchedule;
import com.bloodlink.models.BloodSupply;

public class HospitalDashboardController {
    @FXML
    private TableView<DonationSchedule> tableView;
    @FXML
    private TableColumn<DonationSchedule, LocalDate> colDate;
    @FXML
    private TableColumn<DonationSchedule, String> colDonorName;
    @FXML
    private TableColumn<DonationSchedule, String> colBloodType;
    @FXML
    private TableColumn<DonationSchedule, String> colCode;
    @FXML
    private TableColumn<DonationSchedule, String> colStatus;
    @FXML
    private TableColumn<DonationSchedule, Void> colAction;

    @FXML
    private TableView<BloodSupply> tableBloodSupply;
    @FXML
    private TableColumn<BloodSupply, String> colBloodTypeSupply;
    @FXML
    private TableColumn<BloodSupply, Integer> colUnitsSupply;

    private final ObservableList<DonationSchedule> schedules = FXCollections.observableArrayList();
    private final ObservableList<BloodSupply> bloodSupplies = FXCollections.observableArrayList();
    private final DonationScheduleDAO dao = new DonationScheduleDAO();

    public static int loggedInHospitalId;

    public static void setLoggedInHospitalId(int id) {
        loggedInHospitalId = id;
    }

    @FXML
    private void initialize() {
        // Setup schedule table
        colDate.setCellValueFactory(
                data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDate()));
        colDonorName.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDonorName()));
        colBloodType.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBloodType()));
        colCode.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getIsDone() ? "Complete" : "Pending"));

        // Setup blood supply table
        colBloodTypeSupply.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBloodType()));
        colUnitsSupply.setCellValueFactory(
                data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getUnits()));

        addCompleteButtonToTable();
        loadSchedules();
        loadBloodSupply();
    }

    private void loadSchedules() {
        try {
            schedules.setAll(dao.findByHospitalId(loggedInHospitalId));
            tableView.setItems(schedules);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBloodSupply() {
        try {
            bloodSupplies.setAll(dao.getBloodSupplyByHospitalId(loggedInHospitalId));
            tableBloodSupply.setItems(bloodSupplies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCompleteButtonToTable() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Complete");

            {
                btn.setOnAction(event -> {
                    DonationSchedule ds = getTableView().getItems().get(getIndex());
                    if (!ds.getIsDone()) {
                        TextInputDialog dialog = new TextInputDialog("1");
                        dialog.setTitle("Confirm Donation");
                        dialog.setHeaderText("Enter number of blood units donated:");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(unitsStr -> {
                            try {
                                int units = Integer.parseInt(unitsStr);
                                dao.markAsComplete(ds.getId(), units);
                                loadSchedules();
                                loadBloodSupply();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()).getIsDone()) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }
}
