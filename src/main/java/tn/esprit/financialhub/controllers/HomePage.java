package tn.esprit.financialhub.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePage {
    @FXML
    private Button btn_reclamation;

    @FXML
    private Button btn_reponse;

    @FXML
    private Button btn_statistique;

    @FXML
    private Pane inner_pane;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private AnchorPane side_ankerpane;


    @FXML
    void goreponse(ActionEvent event) {

    }
    @FXML
    void agentReclamation(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
