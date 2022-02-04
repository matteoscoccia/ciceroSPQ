package it.unicam.cs;
import static it.unicam.cs.App.gestoreEsperienze;
public class Turista extends Utente{

    public void prenotaEsperienza(Esperienza esperienza){
        gestoreEsperienze.prenotaEsperienza(esperienza,this);
    }
}
