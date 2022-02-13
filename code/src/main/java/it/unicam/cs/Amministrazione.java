package it.unicam.cs;

import java.sql.SQLException;

import static it.unicam.cs.App.gestoreEsperienze;

public class Amministrazione {

    private String email;

    public Amministrazione(String email) {
        this.email = email;
    }

    public void eliminaAccount() throws SQLException {
        GestoreAccount.adminEliminaAccount();
    }

    public void eliminaEsperienza(Esperienza esperienzaDaEliminare){
        gestoreEsperienze.rimuoviEsperienza(esperienzaDaEliminare,this);
    }
}
