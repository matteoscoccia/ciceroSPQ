package it.unicam.cs.amministrazione;

import it.unicam.cs.esperienza.Esperienza;
import it.unicam.cs.utente.GestoreAccount;
import it.unicam.cs.utente.Utente;

import java.sql.SQLException;
import java.util.Scanner;

import static it.unicam.cs.main.App.*;

public class Amministrazione {

    private String email;

    public Amministrazione(String email) {
        this.email = email;
    }

    public void eliminaAccount() throws SQLException {
        GestoreAccount.adminEliminaAccount();
    }

    //todo correggere
    public void eliminaEsperienza(Esperienza esperienzaDaEliminare){
        gestoreEsperienze.rimuoviEsperienza(esperienzaDaEliminare);
    }

    public void effettuaRicerca(){
        gestoreEsperienze.ricercaConFiltri(new Utente("Amministrazione","","",""));
    }

    public void aggiungiToponimo() throws SQLException {
        gestoreToponimi.registraToponimo();
    }

    public void eliminaToponimo() throws SQLException{
        System.out.println("Specificare il nome del toponimo da eliminare");
        Scanner scanner = new Scanner(System.in);
        String toponimoDaEliminare = scanner.nextLine();
        gestoreToponimi.eliminaToponimo(toponimoDaEliminare);
    }

    public void approvaTag(){
        gestoreAmministazione.approvareTag();
    }
}
