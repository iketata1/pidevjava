package tn.esprit.financialhub.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ReponseR.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Financialhub");
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

    }

    public static void main(String[] args) {
        launch();
    }
}