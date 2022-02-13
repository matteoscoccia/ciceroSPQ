package it.unicam.cs;

import java.sql.SQLException;

public class Utente {

    private final String nome;
    private final String cognome;
    private final String email;
    private final String password;

    public Utente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void contattaAssistenza(){
        Assistenza.getAssistenza(this);
    }

    public void utenteEliminaAccount() throws SQLException {
        //todo to implement
        GestoreAccount.utenteEliminaAccount(this);
    }
}
