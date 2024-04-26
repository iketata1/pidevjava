package tn.esprit.financialhub.models;

import java.time.LocalDate;

public class Reclamation {

    private int id ;
    private String description ;


    private LocalDate date;


    public Reclamation(int id, String description, LocalDate date, String email, String type, String etat, String iduser) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.email = email;
        this.type = type;
        this.etat = etat;
        this.iduser = iduser;
    }

    public Reclamation(String description, LocalDate date, String email, String type, String etat, String iduser) {
        this.description = description;
        this.date = date;
        this.email = email;
        this.type = type;
        this.etat = etat;
        this.iduser = iduser;
    }

    private String email ;

    private String type ;
    private String etat ;
    private String iduser;

    public Reclamation() {

    }

    public LocalDate getDate() {
        return date;
    }



    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getIduser() {
        return this.iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }
}
