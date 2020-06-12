package pl.edu.us.mio.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Klasa główna rozpoczynająca działanie programu
 * @author dexter
 *<!--module-path /opt/javafx-sdk-14.0.1/lib --add-modules=javafx.controls,javafx.fxml -->
 */
public class ProjektMio extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainPanel.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            scene.setCamera(new PerspectiveCamera());
            primaryStage.setTitle("Projekt MIO");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
