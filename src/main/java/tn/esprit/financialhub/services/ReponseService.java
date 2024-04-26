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
            String req = "INSERT INTO reponse(description, idrec, iduser) VALUES('" + reponse.getIduser() + "', '" + reponse.getIdrec() + "', '" + reponse.getIdrec() + "')";
            Statement st = connection.createStatement();

            st.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void modifier(Reponse reponse) throws SQLException {
        String req = "UPDATE reponse SET  description = ? , idrec = ? , iduser = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, reponse.getIduser());
        ps.setString(2, reponse.getIdrec());
        ps.setString(3, reponse.getDescription());


        ps.executeUpdate();

    }

    @Override
    public void supprimer(Reponse reponse) throws SQLException {
        String req = "DELETE FROM reclamation WHERE id = ?";
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
            reponse.setDescription(rs.getString("description"));
            reponse.setIdrec(rs.getString("idrec"));
            reponse.setIduser(rs.getString("iduser"));
            reponses.add(reponse);
        }

        return reponses;
    }
}