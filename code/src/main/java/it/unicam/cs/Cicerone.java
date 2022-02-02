package it.unicam.cs;

import static it.unicam.cs.App.gestoreEsperienze;

public class Cicerone extends Utente{

    public void aggiungiEsperienza(){
        Esperienza esperienzaCreata = gestoreEsperienze.aggiungiEsperienza();
        esperienzaCreata.associaCicerone(this);
    }
}
