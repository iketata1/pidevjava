package tn.esprit.financialhub.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.financialhub.models.Reclamation;
import tn.esprit.financialhub.services.ReclamationService;

import java.sql.SQLException;
import java.util.List;

public class ReponseR {


    @FXML
    private TableColumn<Reclamation, String> colActions;

    @FXML
    private TableColumn<Reclamation, String> coldate;

    @FXML
    private TableColumn<Reclamation, String> coldesc;

    @FXML
    private TableColumn<Reclamation, String> colemail;

    @FXML
    private TableColumn<Reclamation, String> coletat;

    @FXML
    private TableColumn<Reclamation, Integer> colid;

    @FXML
    private TableColumn<Reclamation, String> coliduser;

    @FXML
    private TableColumn<Reclamation, String>coltype;

    @FXML
    private TableView<Reclamation> table;


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

    public void getData(MouseEvent mouseEvent) {
    }


}
