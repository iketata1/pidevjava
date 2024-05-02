package tn.esprit.financialhub.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tn.esprit.financialhub.models.Reclamation;

public class ReponseCell extends TableCell<Reclamation, String> {

    private final Button boutonReponse;

    public ReponseCell() {
        boutonReponse = new Button("Voir la réponse");
        boutonReponse.setOnAction(event -> {
            Reclamation reclamation = getTableView().getItems().get(getIndex());
            String reponse = reclamation.getReponse();
            if (reponse != null && !reponse.isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Réponse");
                alert.setHeaderText(null);
                alert.setContentText(reponse);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Aucune réponse");
                alert.setHeaderText(null);
                alert.setContentText("Aucune réponse n'a été envoyée pour cette réclamation.");
                alert.showAndWait();
            }
        });
    }

    @Override
    protected void updateItem(String reponse, boolean empty) {
        super.updateItem(reponse, empty);
        if (empty || reponse == null || reponse.isEmpty()) {
            setGraphic(null);
        } else {
            setGraphic(boutonReponse);
        }
    }
}
