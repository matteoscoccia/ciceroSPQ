package it.unicam.cs;
import static it.unicam.cs.App.gestoreEsperienze;

public class Amministrazione {

    private String email;

    public Amministrazione(String email) {
        this.email = email;
    }

    public void eliminaEsperienza(Esperienza esperienzaDaEliminare){
        gestoreEsperienze.rimuoviEsperienza(esperienzaDaEliminare,this);
    }
}
