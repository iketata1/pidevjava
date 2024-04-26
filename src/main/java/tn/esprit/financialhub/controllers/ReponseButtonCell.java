package tn.esprit.financialhub.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import tn.esprit.financialhub.models.Reclamation;

public class ReponseButtonCell extends TableCell {
    private final Button button;

    public ReponseButtonCell() {
        this.button = new Button("Réponse");
        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Logique à exécuter lorsque le bouton est cliqué
                Reclamation reclamation = (Reclamation) getTableRow().getItem();
                if (reclamation != null) {
                    // Gérer l'action de réponse à la réclamation ici
                    System.out.println("Répondre à la réclamation " + reclamation.getId());
                }
            }
        });
    }


}
