package tn.esprit.financialhub.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.financialhub.models.Reclamation;
import tn.esprit.financialhub.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public class ReponseR {

    public Button btn_statistique;
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
    private TableColumn<Reclamation, String> coltype;

    @FXML
    private TableView<Reclamation> table;
    @FXML
    private TextField txt_serach;

    @FXML
    private Button btnFiltrerType;

    @FXML
    private Button btnTrier;

    public void refresh() {
        ReclamationService reclamationService = new ReclamationService();
        try {
            List<Reclamation> reclamations = reclamationService.recuperer();
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            table.setItems(observableList);
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            coldesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            coltype.setCellValueFactory(new PropertyValueFactory<>("Type"));
            coletat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("Date"));
            colActions.setCellFactory(new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                @Override
                public TableCell<Reclamation, String> call(TableColumn<Reclamation, String> param) {
                    final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
                        final Button btn = new Button("Reponse");

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btn);

                                btn.setOnAction(event -> {
                                    ouvrirInterfaceReponse(event); // Appel de la méthode
                                });
                            }
                        }
                    };
                    return cell;
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

                return Stream.of(
                                reclamation.getDescription(),
                                reclamation.getEmail(),
                                reclamation.getType(),
                                reclamation.getDate().toString())
                        .anyMatch(field -> field.toLowerCase().contains(lowerCaseFilter));
            });
        });
    }

    public void ouvrirInterfaceReponse(ActionEvent event) {
        Reclamation reclamationSelectionnee = table.getSelectionModel().getSelectedItem();

        if (reclamationSelectionnee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reponse.fxml"));
                Parent root = loader.load();

                ReponseRRControlller reponseController = loader.getController();

                reponseController.colemailselected.setCellValueFactory(new PropertyValueFactory<>("Email"));
                reponseController.coldesc_selected.setCellValueFactory(new PropertyValueFactory<>("Description"));

                ObservableList<Reclamation> selectedData = FXCollections.observableArrayList();
                selectedData.add(reclamationSelectionnee);
                reponseController.table_reponse.setItems(selectedData);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Aucune ligne n'est sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne dans la table.");
            alert.showAndWait();
        }
    }

    @FXML
    private VBox vboxStatistiques;

    public void Statistique(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Statistique.fxml"));
            Parent root = loader.load();
            StatistiqueController statistiqueController = loader.getController();

            // Passer les données de la table au contrôleur de statistiques
            statistiqueController.initialiserDonnees(table.getItems());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getData(MouseEvent mouseEvent) {
        // Méthode vide
    }

    @FXML
    private void filtrerParType(ActionEvent event) {
        List<String> types = getTypesReclamation();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, types);
        dialog.setTitle("Filtrer par type");
        dialog.setHeaderText("Sélectionnez un type de réclamation");
        dialog.setContentText("Type :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(type -> filtrerTableParType(type));
    }

    private List<String> getTypesReclamation() {
        List<String> types = new ArrayList<>();
        for (Reclamation reclamation : table.getItems()) {
            String type = reclamation.getType();
            if (!types.contains(type)) {
                types.add(type);
            }
        }
        return types;
    }

    private void filtrerTableParType(String type) {
        FilteredList<Reclamation> filteredData = new FilteredList<>(table.getItems(), p -> true);

        filteredData.setPredicate(reclamation -> {
            if (type == null || type.isEmpty()) {
                return true;
            }

            return reclamation.getType().equals(type);
        });

        table.setItems(filteredData);
    }

    @FXML
    private void trierReclamations(ActionEvent event) {
        List<String> criteresTri = Arrays.asList("Traitée", "Non traitée");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, criteresTri);
        dialog.setTitle("Trier les réclamations");
        dialog.setHeaderText("Sélectionnez l'état");
        dialog.setContentText("État :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::trierTableParEtat);
    }

    private void trierTableParEtat(String etat) {
        Comparator<Reclamation> comparateur = Comparator.comparing(Reclamation::getEtat, (etat1, etat2) -> {
            if (etat1.equals("Non traitée") && etat2.equals("Traitée")) {
                return -1;
            } else if (etat1.equals("Traitée") && etat2.equals("Non traitée")) {
                return 1;
            } else {
                return etat1.compareTo(etat2);
            }
        });

        ObservableList<Reclamation> reclamationsFiltrees = FXCollections.observableArrayList();
        for (Reclamation reclamation : table.getItems()) {
            if (reclamation.getEtat().equals(etat)) {
                reclamationsFiltrees.add(reclamation);
            }
        }

        FXCollections.sort(reclamationsFiltrees, comparateur);
        table.setItems(reclamationsFiltrees);
    }
}
