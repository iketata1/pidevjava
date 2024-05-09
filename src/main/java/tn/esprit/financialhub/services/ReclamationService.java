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
        String req = "INSERT INTO reclamation(id, email, description, type, etat, date, reponse) VALUES('" + reclamation.getId() + "', '" + reclamation.getEmail() + "', '" + reclamation.getDescription() + "', '" + reclamation.getType() + "', '" + reclamation.getEtat() + "', '" + Date.valueOf(LocalDate.now()) + "', '" + reclamation.getReponse() + "')";

        Statement st = connection.createStatement();
        try {
            st.executeUpdate(req);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Reclamation> rechercherParMail(String email) throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM reclamation WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setId(rs.getInt("ID"));
            reclamation.setDescription(rs.getString("Description"));
            reclamation.setType(rs.getString("Type"));
            reclamation.setEtat(rs.getString("Etat"));
            reclamation.setEmail(rs.getString("Email"));
            reclamation.setDate(rs.getDate("Date").toLocalDate());
            reclamations.add(reclamation);
        }

        return reclamations;
    }

    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET email=?, description=?, type=?, etat=?, date=?, reponse=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, reclamation.getEmail());
        ps.setString(2, reclamation.getDescription());
        ps.setString(3, reclamation.getType());
        ps.setString(4, reclamation.getEtat());
        ps.setDate(5, Date.valueOf(reclamation.getDate()));
        ps.setString(6, reclamation.getReponse());
        ps.setInt(7, reclamation.getId());
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
            reclamation.setDescription(rs.getString("Description"));
            reclamation.setType(rs.getString("Type"));
            reclamation.setEtat(rs.getString("Etat"));
            reclamation.setEmail(rs.getString("Email"));
            reclamation.setDate(rs.getDate("Date").toLocalDate());
            reclamation.setReponse(rs.getString("reponse"));
            reclamations.add(reclamation);
        }

        return reclamations;
    }



    public void repondreReclamation(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET reponse=?, etat='Traitée' WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, reclamation.getReponse());
        ps.setInt(2, reclamation.getId());
        ps.executeUpdate();
    }




}
