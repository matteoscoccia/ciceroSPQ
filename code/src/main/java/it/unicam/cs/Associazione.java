package it.unicam.cs;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.App.gestoreAccount;
import static it.unicam.cs.App.gestoreEsperienze;

public class Associazione extends Utente{

    private ArrayList<String> emailCiceroniAssociati;

    public Associazione(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
    }

    public void aggiungiEsperienza(){
       Esperienza esperienzaCreata = gestoreEsperienze.aggiungiEsperienza();
        checkCiceroniDisponibili(esperienzaCreata);
    }

    public void checkCiceroniDisponibili(Esperienza esperienza){
        // todo GESTIRE DATABASE
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
