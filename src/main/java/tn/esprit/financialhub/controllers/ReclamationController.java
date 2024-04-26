package tn.esprit.financialhub.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.SimpleStyleableStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tn.esprit.financialhub.models.Reclamation;
import tn.esprit.financialhub.services.ReclamationService;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReclamationController {


    @FXML
    private Button btn_ajout;

    @FXML
    private Button btn_ann;

    @FXML
    private Button btn_mod;

    @FXML
    private Button btn_reprec;

    @FXML
    private Button btn_supp;

    @FXML
    private TableColumn<Reclamation, Date> coldate;

    @FXML
    private TableColumn<Reclamation, String> coldesc;

    @FXML
    private TableColumn<Reclamation, String> coletat;

    @FXML
    private TableColumn<Reclamation, Integer> colid;

    @FXML
    private TableColumn<Reclamation, String> coliduser;

    @FXML
    private TableColumn<Reclamation, String> coltype;
    @FXML
    private TableColumn<Reclamation, String> colemail;

    @FXML
    private TextArea desctf;

    @FXML
    private TextField emailtf;

    @FXML
    private TextField idusertf;

    @FXML
    private HBox root;

    @FXML
    private TableView<Reclamation> table;

    @FXML
    private TextField txt_serach;

    @FXML
    private ChoiceBox<String> typetf;




//search

@FXML
    public void initialize() {
        ReclamationService reclamationService = new ReclamationService();
        try {
            List<Reclamation> reclamations = reclamationService.recuperer();
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            table.setItems(observableList);
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            coliduser.setCellValueFactory(new PropertyValueFactory<>("iduser"));
            coldesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            coltype.setCellValueFactory(new PropertyValueFactory<>("Type"));
            coletat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("Email"));
             coldate.setCellValueFactory(new PropertyValueFactory<>("Date"));



        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des reclamations : " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void ajouterRec(ActionEvent event) {
        if (idusertf.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir un nom d'utilisateur.");
            alert.showAndWait();
            return;
        }

        if (!idusertf.getText().matches("[a-zA-Z0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le nom d'utilisateur ne doit contenir que des caractères alphanumériques.");
            alert.showAndWait();
            return;
        }

        if (emailtf.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir une adresse email.");
            alert.showAndWait();
            return;
        }

        if (!emailtf.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir une adresse email valide. exemple@exemple.com");
            alert.showAndWait();
            return;
        }
        if (desctf.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir une description.");
            alert.showAndWait();
            return;
        }
        if (typetf.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un type.");
            alert.showAndWait();
            return;
        }

        ReclamationService reclamationService = new ReclamationService();
        Reclamation reclamation = new Reclamation();
        reclamation.setIduser((idusertf.getText()));
        reclamation.setEmail(emailtf.getText());
        reclamation.setDescription(desctf.getText());
        reclamation.setType((String) typetf.getValue());
        try {
            reclamationService.ajouter(reclamation);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Réclamation ajoutée");
            alert.showAndWait();

            idusertf.clear();
            emailtf.clear();
            desctf.clear();
            typetf.setValue(null);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void annulerRec(ActionEvent event) {

        // Réinitialiser les champs de saisie
        desctf.clear();
        emailtf.clear();
        idusertf.clear();
        typetf.setValue(null);
        btn_mod.setDisable(false);
        table.getSelectionModel().clearSelection(); // Désélectionner toute ligne dans la table

    }

    @FXML
    void getData(MouseEvent event) {
        Reclamation reclamation = table.getSelectionModel().getSelectedItem();
        if (reclamation != null) {
            desctf.setText(reclamation.getDescription());
            idusertf.setText(reclamation.getIduser());
            emailtf.setText(reclamation.getEmail());
            btn_mod.setDisable(false); // Activer le bouton modifier
        }

    }
    @FXML
    void modifierRec(ActionEvent event) {
        Reclamation reclamation = table.getSelectionModel().getSelectedItem();
        if (reclamation != null) {
            reclamation.setDescription(desctf.getText());
            reclamation.setIduser(idusertf.getText());
            reclamation.setEmail(emailtf.getText());

            ReclamationService reclamationService = new ReclamationService();
            try {
                reclamationService.modifier(reclamation); // Appel à la méthode de service pour modifier la réclamation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setContentText("La réclamation a été modifiée avec succès.");
                alert.showAndWait();



                desctf.clear();
                idusertf.clear();
                emailtf.clear();
                // Désactiver le bouton modifier seulement si aucune réclamation n'est sélectionnée
                btn_mod.setDisable(true);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors de la modification de la réclamation : " + e.getMessage());

                alert.showAndWait();
            }
        }
    }


    @FXML
    void reponseRec(ActionEvent event) {

    }

    @FXML
    void supprimerRec(ActionEvent event) {
        Reclamation reclamation = table.getSelectionModel().getSelectedItem();
        if (reclamation != null) {
            ReclamationService reclamationService = new ReclamationService();
            try {
                reclamationService.supprimer(reclamation); // Appel à la méthode de service pour supprimer la réclamation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setContentText("La réclamation a été supprimée avec succès.");
                alert.showAndWait();
                // Rafraîchir la table après la suppression
                initialize();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors de la suppression de la réclamation : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner une réclamation à supprimer.");
            alert.showAndWait();
        }
    }

}
