package it.unicam.cs;

public class Pagamento {

    private final int importo;
    private final Turista turista;
    private final Esperienza esperienza;

    Pagamento(int importo, Turista turista, Esperienza esperienza){
        this.importo = importo;
        this.turista = turista;
        this.esperienza = esperienza;
    }

    // todo add other methods to complete a payment

    public int getImporto(){
       return this.importo;
    }

    public Turista getTurista(){
        return this.turista;
    }

    public Esperienza getEsperienza(){
        return this.esperienza;
    }

}
