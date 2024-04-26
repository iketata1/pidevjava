package tn.esprit.financialhub.services;

import tn.esprit.financialhub.models.Reponse;

import java.sql.SQLException;
import java.util.List;

public interface IService2<T>   {
    void ajouter(T t) throws SQLException;


    void modifier(T t) throws SQLException;
    void supprimer(T t) throws SQLException;
    List<Reponse> recuperer() throws SQLException;

}
