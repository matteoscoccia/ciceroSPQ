package it.unicam.cs;

import static it.unicam.cs.App.gestoreEsperienze;

public class Cicerone extends Utente{

    private String emailAssociazione;

    public Cicerone(String nome, String cognome, String email, String password, String emailAssociazione) {
        super(nome, cognome, email, password);
        this.emailAssociazione = emailAssociazione;
    }

    public void aggiungiEsperienza(){
        Esperienza esperienzaCreata = gestoreEsperienze.aggiungiEsperienza();
        esperienzaCreata.associaCicerone(this);
    }
}
