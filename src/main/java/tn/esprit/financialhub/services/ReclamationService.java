package tn.esprit.financialhub.services;

import tn.esprit.financialhub.models.Reclamation;
import tn.esprit.financialhub.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements IService<Reclamation> {


    private  Connection connection ;
    public ReclamationService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public  void ajouter(Reclamation reclamation) throws SQLException {
        LocalDate date = LocalDate.now();
        String req = "INSERT INTO reclamation(iduser, email, description, type, etat, date) VALUES('" + reclamation.getIduser() + "', '" + reclamation.getEmail() + "', '" + reclamation.getDescription() + "', '" + reclamation.getType() + "', '" + reclamation.getEtat() + "', '" + Date.valueOf(LocalDate.now()) + "')";

        Statement st = connection.createStatement();

        st.executeUpdate(req);
    }

    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        String req ="UPDATE reclamation SET iduser = ? , email = ? , description = ? , etat = ? , type=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,reclamation.getIduser());
        ps.setString(2,reclamation.getEmail());
        ps.setString(3,reclamation.getDescription());
        ps.setString(4,reclamation.getEtat());
        ps.setString(5,reclamation.getType());

        ps.executeUpdate();


    }

    @Override
    public void supprimer(Reclamation reclamation) throws SQLException {
        String req = "DELETE FROM reclamation WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, reclamation.getId());
        ps.executeUpdate();
    }


    public List<Reclamation> recuperer() throws SQLException {

        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM reclamation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setId(rs.getInt("ID"));
            reclamation.setIduser(rs.getString("IDUser"));
            reclamation.setDescription(rs.getString("Description"));
            reclamation.setType(rs.getString("Type"));
            reclamation.setEtat(rs.getString("Etat"));
            reclamation.setEmail(rs.getString("Email"));
            reclamation.setDate(rs.getDate("Date").toLocalDate());
            reclamations.add(reclamation);
        }

        return reclamations;
    }







}
