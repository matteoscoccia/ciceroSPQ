package it.unicam.cs;

import java.util.ArrayList;
import java.util.Date;

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
}
