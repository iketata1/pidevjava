
package tn.esprit.financialhub.controllers;
import javafx.beans.property.SimpleObjectProperty;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.HBox;
        import tn.esprit.financialhub.models.Reclamation;
        import tn.esprit.financialhub.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Date;
        import java.util.List;

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
    private TableColumn<Reclamation, String> coltype;
    @FXML
    private TableColumn<Reclamation, String> colemail;

    @FXML
    private TextArea desctf;

    @FXML
    private TextArea emailtf;
    @FXML
    private TableColumn<Reclamation, String> colreponse;


    @FXML
    private HBox root;

    @FXML
    private TableView<Reclamation> table;

    @FXML
    private TextField txt_serach;

    @FXML
    private ChoiceBox<String> typetf;




//search

    public void refresh() {
        ReclamationService reclamationService = new ReclamationService();
        try {
            List<Reclamation> reclamations = reclamationService.recuperer();
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            FilteredList<Reclamation> filteredData = new FilteredList<>(observableList, p -> true);
            table.setItems(filteredData);
            coldesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            coltype.setCellValueFactory(new PropertyValueFactory<>("Type"));
            coletat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("Date"));
            colreponse.setCellValueFactory(new PropertyValueFactory<>("reponse"));



        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des reclamations : " + e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    public void initialize() {
        refresh();

        // Créer une FilteredList pour filtrer les données de la table
        FilteredList<Reclamation> filteredData = new FilteredList<>(table.getItems(), p -> true);

        // Lier la FilteredList à la table
        table.setItems(filteredData);

        // Ajouter un EventHandler pour le champ de recherche
        txt_serach.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reclamation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (reclamation.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reclamation.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reclamation.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reclamation.getDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }


    @FXML
    void ajouterRec(ActionEvent event) throws SQLException {


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
        reclamation.setEmail(emailtf.getText());
        reclamation.setDescription(desctf.getText());
        reclamation.setType((String) typetf.getValue());
        reclamation.setEtat("Non traitée");

        try {
            reclamationService.ajouter(reclamation);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Réclamation ajoutée");
            alert.showAndWait();

            emailtf.clear();
            desctf.clear();
            typetf.setValue(null);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }




        refresh();



    }


    @FXML
    void annulerRec(ActionEvent event) {

        // Réinitialiser les champs de saisie
        desctf.clear();
        emailtf.clear();
        typetf.setValue(null);
        btn_mod.setDisable(false);
        table.getSelectionModel().clearSelection(); // Désélectionner toute ligne dans la table

    }
    @FXML
    private void getData(MouseEvent event) {
        Reclamation selectedReclamation = table.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            desctf.setText(selectedReclamation.getDescription());
            emailtf.setText(selectedReclamation.getEmail());
            typetf.setValue(selectedReclamation.getType());
            btn_mod.setDisable(false); // Activer le bouton "Modifier"

            if (selectedReclamation.getReponse() != null) {
                btn_reprec.setDisable(false); // Activer le bouton "reponse"
            } else {
                btn_reprec.setDisable(true); // Désactiver le bouton "reponse"
            }
        } else {
            desctf.clear();
            emailtf.clear();
            typetf.setValue(null);
            btn_reprec.setDisable(true); // Désactiver le bouton "reponse"
            btn_mod.setDisable(true); // Désactiver le bouton "Modifier"
        }
    }


    @FXML
    void modifierRec(ActionEvent event) {
        Reclamation reclamation = table.getSelectionModel().getSelectedItem();
        if (reclamation != null) {
            reclamation.setDescription(desctf.getText());
            reclamation.setEmail(emailtf.getText());
            reclamation.setType(typetf.getValue());
            ReclamationService reclamationService = new ReclamationService();
            try {
                reclamationService.modifier(reclamation); // Appel à la méthode de service pour modifier la réclamation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setContentText("La réclamation a été modifiée avec succès.");
                alert.showAndWait();

                btn_mod.setDisable(true); // Désactiver le bouton "Modifier" après la modification
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors de la modification de la réclamation : " + e.getMessage());
                alert.showAndWait();
            }
        }
        refresh();
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

    @FXML
    private void afficherReponse(ActionEvent event) {
        Reclamation selectedReclamation = table.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            // Récupérer le contenu de la réponse depuis un champ de texte ou une autre source
            String contenuReponse = "Contenu de la réponse";

            // Affecter directement le contenu de la réponse à la propriété reponse de la classe Reclamation
            selectedReclamation.setReponse(contenuReponse);

            // Mettre à jour la réclamation dans la base de données
            ReclamationService reclamationService = new ReclamationService();
            try {
                reclamationService.repondreReclamation(selectedReclamation);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réponse envoyée");
                alert.setHeaderText(null);
                alert.setContentText("Votre réponse a été envoyée avec succès.");
                alert.showAndWait();
                refresh(); // Rafraîchir la table après l'envoi de la réponse
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'envoi de la réponse : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }


}
