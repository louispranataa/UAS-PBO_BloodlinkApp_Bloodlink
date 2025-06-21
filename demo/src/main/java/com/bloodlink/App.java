package com.bloodlink;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // ✅ Attempt to connect to MySQL
        try (Connection conn = DbUtil.getConnection()) {
            System.out.println("✅ Connected to MySQL database!");
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to MySQL:");
            e.printStackTrace();
        }

        // ✅ Load JavaFX UI
        scene = new Scene(loadFXML("donor_login"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("BloodLink App");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
