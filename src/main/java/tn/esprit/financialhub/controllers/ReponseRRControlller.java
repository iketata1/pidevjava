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
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
                // Mettre à jour la réclamation avec la réponse
                reclamationService.modifier(reclamationSelectionnee);
                // Envoyer la réponse par email
                envoyerReponseParEmail(reclamationSelectionnee); // Utilisation de l'adresse email de la réclamation sélectionnée
                // Afficher une confirmation à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réponse envoyée");
                alert.setContentText("La réponse a été envoyée avec succès.");
                alert.showAndWait();
                // Rafraîchir la table des réclamations
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

    // Méthode pour envoyer la réponse par email en utilisant l'adresse email de la réclamation sélectionnée
    private void envoyerReponseParEmail(Reclamation reclamation) {
        String destinataire = reclamation.getEmail(); // Récupération de l'adresse email de la réclamation sélectionnée
        String objet = "Réponse à votre réclamation";
        String contenu = "Bonjour,\n\nVoici la réponse à votre réclamation :\n\n" + reclamation.getReponse();

        // Configuration des propriétés pour la session d'envoi d'email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Activation de l'authentification
        props.put("mail.smtp.starttls.enable", "true"); // Activation du protocole TLS
        props.put("mail.smtp.host", "smtp.gmail.com"); // Hôte SMTP de Gmail
        props.put("mail.smtp.port", "587"); // Port SMTP de Gmail

        // Création de la session d'envoi d'email
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ines.ketata@esprit.tn", "222JFT3747"); // Remplacer par vos identifiants
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ines.ketata@esprit.tn")); // Remplacer par votre adresse email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(objet);
            message.setText(contenu);

            // Envoi du message
            Transport.send(message);
            System.out.println("Email envoyé avec succès à " + destinataire);
        } catch (MessagingException e) {
            System.out.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }


}

