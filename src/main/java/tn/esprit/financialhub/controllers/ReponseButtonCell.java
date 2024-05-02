package tn.esprit.financialhub.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.Alert;
import tn.esprit.financialhub.models.Reclamation;

public class ReponseButtonCell extends TableCell<Reclamation, Reclamation> {

    private final Button reponseButton = new Button("Voir la réponse");

    public ReponseButtonCell() {
        reponseButton.getStyleClass().add("reponse-button"); // Appliquer le style CSS

        reponseButton.setOnAction(event -> {
            Reclamation reclamation = getItem();
            if (reclamation != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réponse");
                alert.setHeaderText(null);
                if (reclamation.getReponse() != null) {
                    alert.setContentText(reclamation.getReponse());
                } else {
                    alert.setContentText("Aucune réponse pour cette réclamation.");
                }
                alert.showAndWait();
            }
        });
    }

    @Override
    protected void updateItem(Reclamation reclamation, boolean empty) {
        super.updateItem(reclamation, empty);
        if (empty || reclamation == null) {
            setGraphic(null);
        } else {
            setGraphic(reponseButton);
        }
    }

}
