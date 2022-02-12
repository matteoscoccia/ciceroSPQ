package it.unicam.cs;
import static it.unicam.cs.App.gestoreEsperienze;
public class Turista extends Utente{

    public Turista(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
    }

    public void prenotaEsperienza(Esperienza esperienza){
        gestoreEsperienze.prenotaEsperienza(esperienza,this);
    }
}
