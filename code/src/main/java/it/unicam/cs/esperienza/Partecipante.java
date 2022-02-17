package it.unicam.cs.esperienza;

public class Partecipante {

    private String nome;
    private String cognome;
    private String email;
    public Partecipante(String nome, String cognome, String email){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
