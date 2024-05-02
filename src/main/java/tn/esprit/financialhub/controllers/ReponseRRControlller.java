package tn.esprit.financialhub.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.financialhub.models.Reclamation;
import tn.esprit.financialhub.services.ReclamationService;

import java.sql.SQLException;
import java.util.List;

public class ReponseRRControlller {

    @FXML
    private Button btn_reprec;

    @FXML
    public TableColumn<Reclamation, String> coldesc_selected;

    @FXML
    public TableColumn<Reclamation, String> colemailselected;

    @FXML
    private TextArea descReponseTF;

    @FXML
    public TableView<Reclamation> table_reponse;

    @FXML
    private TextField txt_serach;

    private ReclamationService reclamationService;
    private ObservableList<Reclamation> observableList;
    private Stage currentStage;

    @FXML
    public void initialize() {
        reclamationService = new ReclamationService();
        refreshTableView();

        coldesc_selected.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colemailselected.setCellValueFactory(new PropertyValueFactory<>("Email"));
        TableColumn<Reclamation, String> colreponse = new TableColumn<>("Réponse");
        colreponse.setCellValueFactory(new PropertyValueFactory<>("reponse"));
        colreponse.setCellFactory(param -> new ReponseCell());
        table_reponse.getColumns().add(colreponse);
       // currentStage = (Stage) table_reponse.getScene().getWindow();
    }


    private void refreshTableView() {
        try {
            List<Reclamation> reclamations = reclamationService.recuperer();
            observableList = FXCollections.observableList(reclamations);
            table_reponse.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des reclamations : " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void ajouterRep(ActionEvent actionEvent) {
        Reclamation reclamationSelectionnee = table_reponse.getSelectionModel().getSelectedItem();

        if (reclamationSelectionnee != null) {
            String reponse = descReponseTF.getText();
            reclamationSelectionnee.setReponse(reponse);

            try {
                reclamationService.modifier(reclamationSelectionnee);
                reclamationService.repondreReclamation(reclamationSelectionnee); // Mettre à jour l'état de la réclamation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réponse envoyée");
                alert.setContentText("La réponse a été envoyée avec succès.");
                alert.showAndWait();
                refreshTableView();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors de l'envoi de la réponse : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner une réclamation pour envoyer une réponse.");
            alert.showAndWait();
        }
    }


}

