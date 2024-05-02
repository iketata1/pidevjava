package tn.esprit.financialhub.models;

import java.time.LocalDate;

public class Reclamation {

    private int id ;
    private String description ;
    private String reponse;


    private LocalDate date;


    public Reclamation(int id, String description, LocalDate date, String email, String type, String etat,String reponse) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.email = email;
        this.type = type;
        this.etat = etat;
        this.reponse = reponse;

    }

    public Reclamation(String description, LocalDate date, String email, String type, String etat,String reponse) {
        this.description = description;
        this.date = date;
        this.email = email;
        this.type = type;
        this.etat = etat;
        this.reponse = reponse;

    }

    private String email ;

    private String type ;
    private String etat ;

    public Reclamation() {

    }

    public LocalDate getDate() {
        return date;
    }


    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
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




    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setReponse(Reponse reponse) {
    }
}
