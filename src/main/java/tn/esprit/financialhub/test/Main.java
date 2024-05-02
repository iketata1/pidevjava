package tn.esprit.financialhub.test;

import tn.esprit.financialhub.models.Reponse;
import tn.esprit.financialhub.services.ReclamationService;
import tn.esprit.financialhub.services.ReponseService;
import tn.esprit.financialhub.utils.MyDatabase;

public class Main {


    public static void main(String[] args) {
ReponseService  rs = new ReponseService();
        Reponse r = new Reponse();
        r.setContenuReponse("Bonjour, voici la reponse a votre reclamation");
        rs.ajouter(r);

        MyDatabase db =MyDatabase.getInstance();
        MyDatabase db2 =MyDatabase.getInstance();
 System.out.println(db);
        System.out.println(db2);




        //  rs.supprimer(1);
//            System.out.println(r.recuperer());
    }

}
