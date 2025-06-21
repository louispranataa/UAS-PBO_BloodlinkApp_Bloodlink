module com.bloodlink {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bloodlink to javafx.fxml;
    opens com.bloodlink.controllers to javafx.fxml;

    exports com.bloodlink;
}
