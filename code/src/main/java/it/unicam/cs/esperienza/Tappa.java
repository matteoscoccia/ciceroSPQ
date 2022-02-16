package it.unicam.cs.esperienza;

public class Tappa {

    private String nome;
    private String  descrizione;
    private String indirizzo;

    public Tappa(String nomeTappa, String descrizioneTappa, String indirizzoTappa) {
        this.nome = nomeTappa;
        this.descrizione = descrizioneTappa;
        this.indirizzo = indirizzoTappa;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
}
