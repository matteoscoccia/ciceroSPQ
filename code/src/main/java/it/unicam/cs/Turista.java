package it.unicam.cs;
import static it.unicam.cs.App.gestoreEsperienze;
public class Turista extends Utente{

    public Turista(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
    }

    public void prenotaEsperienza(Esperienza esperienza){
        gestoreEsperienze.prenotaEsperienza(esperienza,this);
    }

    public void visualizzaProfilo(){
        System.out.println("Nome : " +this.getNome());
        System.out.println("Cognome : " +this.getCognome());
        System.out.println("Email : " +this.getEmail());
    }
}
