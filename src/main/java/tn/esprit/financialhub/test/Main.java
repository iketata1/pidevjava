package tn.esprit.financialhub.test;

import tn.esprit.financialhub.services.ReclamationService;
import tn.esprit.financialhub.utils.MyDatabase;

public class Main {


    public static void main(String[] args) {
ReclamationService rs = new ReclamationService();
        MyDatabase db =MyDatabase.getInstance();
        MyDatabase db2 =MyDatabase.getInstance();
 System.out.println(db);
        System.out.println(db2);




        //  rs.supprimer(1);
//            System.out.println(r.recuperer());
    }

}
