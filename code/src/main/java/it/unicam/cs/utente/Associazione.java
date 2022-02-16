package it.unicam.cs.utente;

import it.unicam.cs.storage.DBManager;
import it.unicam.cs.esperienza.Esperienza;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static it.unicam.cs.main.App.gestoreAccount;
import static it.unicam.cs.main.App.gestoreEsperienze;

public class Associazione extends Utente {

    private ArrayList<String> emailCiceroniAssociati;

    public Associazione(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
    }

    public void aggiungiEsperienza(){
       Esperienza esperienzaCreata = gestoreEsperienze.aggiungiEsperienza();
       checkCiceroniDisponibili(esperienzaCreata);
       System.out.println("Esperienza registrata");
    }

    public void checkCiceroniDisponibili(Esperienza esperienzaCreata){
        ArrayList<String> emailCiceroniDisponibili = DBManager.selezionaCiceroniDisponibili(this.getEmail(), esperienzaCreata.getData());
        Scanner inputScanner = new Scanner(System.in);

        if(emailCiceroniDisponibili.size() == 0){
            System.out.println("Non ci sono ciceroni disponibili per la data selezionata");
            return;
        }
        int count;
        int scelta = 0;
        do{
            count = 1;
            System.out.println("\nCiceroni Disponibili:");
            for (String e:
                    emailCiceroniDisponibili) {
                System.out.println(count+")"+e);
                count++;
            }
            System.out.println("Inserire numero cicerone: ");
            scelta = Integer.parseInt(inputScanner.nextLine());
        } while(scelta<1 || scelta>count);
        String emailCiceroneScelto = emailCiceroniDisponibili.get(scelta-1);
        DBManager.registraGuidaEsperienza(new Cicerone("","",emailCiceroneScelto,"",""), esperienzaCreata.getId());
    }

    public void associaCicerone() throws SQLException {
        gestoreAccount.aggiungiCicerone(this);
    }

    public void setEmailCiceroniAssociati(ArrayList<String> emailCiceroniAssociati) {
        this.emailCiceroniAssociati = emailCiceroniAssociati;
    }

    public ArrayList<String> getEmailCiceroniAssociati() {
        return emailCiceroniAssociati;
    }

    public void rimuoviCicerone(){
        gestoreAccount.rimuoviCicerone(this);
    }

    public void visualizzaProfilo(){
        System.out.println("Nome : " +this.getNome());
        System.out.println("Cognome : " +this.getCognome());
        System.out.println("Email : " +this.getEmail());
        ArrayList<Cicerone> listaCiceroniAssociati;
        listaCiceroniAssociati = DBManager.listaCiceroniAssociati(this.getEmail());
        System.out.println("LISTA CICERONI ASSOCIATI A QUESTA ASSOCIAZIONE");
        for (Cicerone cicerone : listaCiceroniAssociati) {
            System.out.println(cicerone.getEmail());
        }
    }
}
