package app;

import database.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseInitializer.inicializar();

        Parent root = FXMLLoader.load(
            getClass().getResource("/controller/Dashboard.fxml")
        );

        Scene scene = new Scene(root, 1000, 650);
        

        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.setMinWidth(1000);
        stage.setMinHeight(650);
        stage.setTitle("FinTrack");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}