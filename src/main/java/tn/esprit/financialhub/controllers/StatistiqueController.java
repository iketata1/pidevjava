package tn.esprit.financialhub.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import tn.esprit.financialhub.models.Reclamation;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class StatistiqueController {
    @FXML
    private VBox vboxStatistiques;

    @FXML
    private PieChart pieChartStatistiques;

    public void initialiserDonnees(ObservableList<Reclamation> donnees) {
        // Calculer les statistiques pour chaque type de réclamation
        Map<String, Integer> statistiques = new HashMap<>();
        int totalReclamations = donnees.size();

        for (Reclamation reclamation : donnees) {
            String type = reclamation.getType();
            statistiques.put(type, statistiques.getOrDefault(type, 0) + 1);
        }

        // Effacer les données précédentes du PieChart
        pieChartStatistiques.getData().clear();

        // Ajouter les données au PieChart
        for (Map.Entry<String, Integer> entry : statistiques.entrySet()) {
            String type = entry.getKey();
            int nombreReclamations = entry.getValue();
            double pourcentage = (double) nombreReclamations / totalReclamations * 100;

            PieChart.Data data = new PieChart.Data(type + " (" + String.format("%.2f", pourcentage) + "%)", nombreReclamations);
            pieChartStatistiques.getData().add(data);
        }

        // Appeler la méthode d'animation après l'initialisation des données
        animerPieChart();
    }

    private void animerPieChart() {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), pieChartStatistiques);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
    }
}
