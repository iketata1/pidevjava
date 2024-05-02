package tn.esprit.financialhub.services;

import tn.esprit.financialhub.models.Reclamation;
import tn.esprit.financialhub.models.Reponse;
import tn.esprit.financialhub.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public  class ReponseService implements IService2<Reponse> {

    private Connection connection;

    public ReponseService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reponse reponse) {
        try {
            // Insérer la nouvelle réponse dans la table reponse avec une valeur par défaut pour contenuReponse
            String reqReponse = "INSERT INTO reponse(contenuReponse) VALUES('')";
            PreparedStatement psReponse = connection.prepareStatement(reqReponse, Statement.RETURN_GENERATED_KEYS);
            psReponse.executeUpdate();

            // Récupérer l'ID de la nouvelle réponse
            ResultSet rsReponse = psReponse.getGeneratedKeys();
            int reponseId = -1;
            if (rsReponse.next()) {
                reponseId = rsReponse.getInt(1);
            }

            // Récupérer l'ID de la réclamation correspondante
            String reqReclamationId = "SELECT reclamation_id FROM reponse_reclamation WHERE reponse_id = ?";
            PreparedStatement psReclamationId = connection.prepareStatement(reqReclamationId);
            psReclamationId.setInt(1, reponseId);
            ResultSet rsReclamationId = psReclamationId.executeQuery();
            int reclamationId = -1;
            if (rsReclamationId.next()) {
                reclamationId = rsReclamationId.getInt(1);
            }

            // Récupérer la valeur de la colonne reponse de la table reclamation
            String reqReponseReclamation = "SELECT reponse FROM reclamation WHERE id = ?";
            PreparedStatement psReponseReclamation = connection.prepareStatement(reqReponseReclamation);
            psReponseReclamation.setInt(1, reclamationId);
            ResultSet rsReponseReclamation = psReponseReclamation.executeQuery();
            String reponseReclamation = null;
            if (rsReponseReclamation.next()) {
                reponseReclamation = rsReponseReclamation.getString(1);
            }

            // Mettre à jour la colonne contenuReponse avec la valeur de la colonne reponse de la table reclamation
            String reqUpdateReponse = "UPDATE reponse SET contenuReponse = ? WHERE id = ?";
            PreparedStatement psUpdateReponse = connection.prepareStatement(reqUpdateReponse);
            psUpdateReponse.setString(1, reponseReclamation);
            psUpdateReponse.setInt(2, reponseId);
            psUpdateReponse.executeUpdate();

            // Mettre à jour l'état de la réclamation correspondante
            String reqUpdateEtat = "UPDATE reclamation SET etat = 'Traitée' WHERE id = ?";
            PreparedStatement psUpdateEtat = connection.prepareStatement(reqUpdateEtat);
            psUpdateEtat.setInt(1, reclamationId);
            psUpdateEtat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }










    @Override
    public void modifier(Reponse reponse) throws SQLException {
        String req = "UPDATE reponse SET  description = ? ";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, reponse.getContenuReponse());


        ps.executeUpdate();

    }

    @Override
    public void supprimer(Reponse reponse) throws SQLException {
        String req = "DELETE FROM reponse WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, reponse.getId());
        ps.executeUpdate();
    }


    @Override
    public List<Reponse> recuperer() throws SQLException {

        List<Reponse> reponses = new ArrayList<>();
        String req = "SELECT * FROM reponse";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Reponse reponse = new Reponse();

            reponse.setId(rs.getInt("id"));
            reponse.setContenuReponse(rs.getString("description"));
            reponses.add(reponse);
        }

        return reponses;
    }
}