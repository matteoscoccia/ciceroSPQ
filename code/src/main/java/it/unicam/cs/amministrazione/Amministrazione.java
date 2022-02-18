package it.unicam.cs.amministrazione;

import it.unicam.cs.esperienza.Esperienza;
import it.unicam.cs.utente.GestoreAccount;
import it.unicam.cs.utente.Utente;

import java.sql.SQLException;

import static it.unicam.cs.main.App.gestoreEsperienze;

public class Amministrazione {

    private String email;

    public Amministrazione(String email) {
        this.email = email;
    }

    public void eliminaAccount() throws SQLException {
        GestoreAccount.adminEliminaAccount();
    }

    public void eliminaEsperienza(Esperienza esperienzaDaEliminare){
        gestoreEsperienze.rimuoviEsperienza(esperienzaDaEliminare);
    }

    public void effettuaRicerca(){
        gestoreEsperienze.ricercaConFiltri(new Utente("Amministrazione","","",""));
    }
}
