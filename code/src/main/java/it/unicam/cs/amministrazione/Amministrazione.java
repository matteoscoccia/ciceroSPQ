package it.unicam.cs.amministrazione;

import it.unicam.cs.esperienza.Esperienza;
import it.unicam.cs.esperienza.Tag;
import it.unicam.cs.utente.GestoreAccount;
import it.unicam.cs.utente.Utente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static it.unicam.cs.main.App.*;

public class Amministrazione {

    private final String email;
    Scanner input = new Scanner(System.in);

    public Amministrazione(String email) {
        this.email = email;
    }

    public void eliminaAccount() throws SQLException {
        GestoreAccount.adminEliminaAccount();
    }

    public void eliminaEsperienza(Esperienza esperienzaDaEliminare){
        gestoreEsperienze.rimuoviEsperienza(esperienzaDaEliminare);
    }

    public void effettuaRicerca(){
        gestoreEsperienze.ricercaConFiltri(new Utente("Amministrazione","","",""));
    }

    public void aggiungiToponimo() throws SQLException {
        gestoreToponimi.registraToponimo();
    }

    public void eliminaToponimo() throws SQLException{
        System.out.println("Specificare il nome del toponimo da eliminare");
        Scanner scanner = new Scanner(System.in);
        String toponimoDaEliminare = scanner.nextLine();
        gestoreToponimi.eliminaToponimo(toponimoDaEliminare);
    }

    public void approvareTag(){
        ArrayList<Tag> tagDaApprovare = GestoreTag.getTagDaApprovare();

        if(tagDaApprovare.isEmpty()){
            System.out.println("Non ci sono tag da approvare");
        }else{
            for(int i=1;i<=tagDaApprovare.size();i++){
                System.out.println(i+" | "+tagDaApprovare.get(i-1).getName() );
            }
            int choice;
                do {
                    System.out.println("0 per uscire ");
                    System.out.println("Inserire il numero del tag da approvare");
                    choice = Integer.parseInt(input.nextLine());
                } while (choice < 0 || choice > tagDaApprovare.size());

                if(choice!=0) {
                    String conferma;
                    do {
                        System.out.println("Confermare inserimento Tag " + tagDaApprovare.get(choice - 1).getName() + " S/N");
                        conferma = input.nextLine();
                    } while (!(conferma.equals("S") || conferma.equals("N")));
                    if (conferma.equals("S")) {
                        GestoreTag.aggiungiNuovoTag(tagDaApprovare.get(choice - 1));
                        GestoreTag.rimuoviTagDaApprovare(tagDaApprovare.get(choice - 1));
                        System.out.println("Tag Approvato");
                    } else {
                        System.out.println("Approvazione tag annullata ");
                    }
                }
        }
    }

    public void definireTag(){
        String conferma,tag;
        do{
            System.out.println("Inserire il nome del nuovo tag che si vuole definire");
            tag= input.nextLine();
            System.out.println("Confermare tag S/N");
            conferma = input.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")){
            Tag tagDaDefinire = new Tag(tag);
            if(!GestoreTag.controlloTag(tagDaDefinire)){
                GestoreTag.aggiungiNuovoTag(tagDaDefinire);
                System.out.println("TAG definito correttamente");
            }else{
                System.out.println("Tag già presente");
            }

        }else{
            System.out.println("Definizione tag annullata");
        }

    }
}
