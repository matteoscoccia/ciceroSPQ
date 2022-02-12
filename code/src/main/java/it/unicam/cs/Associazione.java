package it.unicam.cs;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.App.gestoreAccount;
import static it.unicam.cs.App.gestoreEsperienze;

public class Associazione extends Utente{

    private ArrayList<Cicerone> ciceroniAssociati;

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
}
